package com.x.security;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.OnCommittedResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/6 16:37
 * @Description:
 */
public class CustomHeaderWriterFilter extends HeaderWriterFilter {

    private final List<HeaderWriter> headerWriters;

    public CustomHeaderWriterFilter(List<HeaderWriter> headerWriters) {
        super(headerWriters);
        this.headerWriters = headerWriters;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHeaderWriterFilter.HeaderWriterResponse headerWriterResponse = new CustomHeaderWriterFilter.HeaderWriterResponse(request, response, this.headerWriters);
        HeaderHttpRequestWrapper headerHttpRequestWrapper = new HeaderHttpRequestWrapper(request);
        try {
            filterChain.doFilter(headerHttpRequestWrapper, headerWriterResponse);
        } finally {
            headerWriterResponse.writeHeaders();
        }

    }

    static class HeaderWriterResponse extends OnCommittedResponseWrapper {
        private final HttpServletRequest request;
        private final List<HeaderWriter> headerWriters;

        HeaderWriterResponse(HttpServletRequest request, HttpServletResponse response, List<HeaderWriter> headerWriters) {
            super(response);
            this.request = request;
            this.headerWriters = headerWriters;
        }

        @Override
        protected void onResponseCommitted() {
            this.writeHeaders();
            this.disableOnResponseCommitted();
        }

        protected void writeHeaders() {
            if(!this.isDisableOnResponseCommitted()) {
                Iterator var1 = this.headerWriters.iterator();

                while(var1.hasNext()) {
                    HeaderWriter headerWriter = (HeaderWriter)var1.next();
                    headerWriter.writeHeaders(this.request, this.getHttpResponse());
                }

            }
        }

        private HttpServletResponse getHttpResponse() {
            return (HttpServletResponse)this.getResponse();
        }
    }
}