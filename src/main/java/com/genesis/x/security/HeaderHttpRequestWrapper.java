package com.genesis.x.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liuxing
 * @Date: 2018/12/6 17:09
 * @Description:
 */
@Slf4j
public class HeaderHttpRequestWrapper extends HttpServletRequestWrapper{

    private ConcurrentHashMap headers = new ConcurrentHashMap<String, String>();

    private byte[] requestBody = null;

    public HeaderHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("Wrap requestBody failed:{}", e.getMessage());
        }
    }

    public void setHeader(String name, String value){
        this.headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if(StringUtils.isEmpty(header)){
            Object o = this.headers.get(name);
            return o == null?null:o.toString();
        }
        return header;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(this.headers.keySet());
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        return Collections.enumeration(set);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;
        private boolean finished = false;

        public DelegatingServletInputStream(InputStream sourceStream) {
            Assert.notNull(sourceStream, "Source InputStream must not be null");
            this.sourceStream = sourceStream;
        }

        public final InputStream getSourceStream() {
            return this.sourceStream;
        }

        @Override
        public int read() throws IOException {
            int data = this.sourceStream.read();
            if(data == -1) {
                this.finished = true;
            }

            return data;
        }

        @Override
        public int available() throws IOException {
            return this.sourceStream.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        @Override
        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }

}