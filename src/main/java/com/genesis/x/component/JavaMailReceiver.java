package com.genesis.x.component;

import com.genesis.x.dto.EmailDto;
import com.genesis.x.utils.MailParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountListener;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @Author liuxing
 * @Date 2020/3/11 17:44
 * @Version 1.0
 * @Description:
 */
@Slf4j
@Component
public class JavaMailReceiver implements InitializingBean, DisposableBean {

    private Properties properties;
    private Session session;
    private Store store;
    private Folder folder;
    private boolean expunge = true;

    @Autowired
    private ReceiveMailProperty property;

    @Override
    public void afterPropertiesSet() {
        properties = new Properties();
        properties.setProperty("mail.store.protocol", property.getProtocol());
        properties.setProperty("mail."+ property.getProtocol() +".host", property.getHost());
        properties.setProperty("mail."+ property.getProtocol() +".port", property.getPort());
        properties.setProperty("mail."+ property.getProtocol() +".auth", property.getAuth());
        properties.setProperty("mail."+ property.getProtocol() +".ssl.enable", property.getSslEnable());
        properties.setProperty("mail.debug", property.getDebug());

        session = Session.getInstance(properties);

        log.info("---------------JavaMailReceiver session init---------------");
        // 影响启动时间 不初始化
//        try {
//            store = session.getStore();
//            store.connect( property.getUsername(), property.getPassword());
//
//            folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_WRITE);
//            log.info("---------------JavaMailReceiver init---------------");
//        } catch (MessagingException e) {
//            log.error("---------------JavaMailReceiver init fail:{}---------------", e.getMessage());
//        }
    }

    public boolean isStoreConnected(){
        if(store != null && store.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isFolderConnected(){
        if(folder != null && folder.isOpen()){
            return true;
        } else {
            return false;
        }
    }

    public void connect() throws MessagingException {
        if(!this.isStoreConnected()){
            store = session.getStore();
            store.connect( property.getUsername(), property.getPassword());
        }

        if(!this.isFolderConnected()){
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
        }
    }

    public List<EmailDto> getMessages(SearchTerm searchTerm, boolean isDel) throws MessagingException, IOException {
        this.connect();

        Message[] messages;
        List<EmailDto> emailDtos = new ArrayList<>();
        if(!this.hasUnreadMessage()){
            return emailDtos;
        }
        if(searchTerm == null){
            messages = folder.getMessages();
        } else {
            messages = folder.search(searchTerm);
        }
        for (Message message : messages) {
            MimeMessage mimeMessage = (MimeMessage) message;
            EmailDto emailDto = new EmailDto();
            emailDto.setMessageId(mimeMessage.getMessageID());
            emailDto.setSubject(MailParseUtils.getSubject(mimeMessage));

            StringBuffer content = new StringBuffer(256);
            MailParseUtils.getMailTextContent(mimeMessage, content);
            emailDto.setHtmlContent(content.toString());
            emailDto.setPlainTextContent(Jsoup.parse(content.toString()).text());

            emailDto.setFrom(MailParseUtils.getFrom(mimeMessage));
            emailDto.setReplyTo(MailParseUtils.getReplyTo(mimeMessage));
            emailDto.setTo(MailParseUtils.getReceiveAddress(mimeMessage, Message.RecipientType.TO));
            emailDto.setCc(MailParseUtils.getReceiveAddress(mimeMessage, Message.RecipientType.CC));
            emailDto.setBcc(MailParseUtils.getReceiveAddress(mimeMessage, Message.RecipientType.BCC));
            emailDto.setPersonal(MailParseUtils.getPersonal(mimeMessage));
            emailDto.setPriority(MailParseUtils.getPriority(mimeMessage));
            emailDto.setReplySign(MailParseUtils.isReplySign(mimeMessage));
            emailDto.setSeen(MailParseUtils.isSeen(mimeMessage));
            emailDto.setContainerAttachment(MailParseUtils.isContainAttachment(mimeMessage));
            emailDto.setSendDate(mimeMessage.getSentDate());
            emailDto.setReceiveDate(mimeMessage.getReceivedDate());
            HashMap<String, InputStream> files = new HashMap<>();
            MailParseUtils.getAttachments(mimeMessage, files);
            emailDto.setFiles(files);
            emailDto.setSize(mimeMessage.getSize());

            emailDtos.add(emailDto);
        }
        if(isDel){
            this.delete(messages);
        }
        return emailDtos;
    }

    public List<EmailDto> getMessages(boolean isDel) throws MessagingException, IOException {
        return this.getMessages(null, isDel);
    }

    public List<EmailDto> getMessages() throws MessagingException, IOException {
        return this.getMessages(null, true);
    }

    public boolean hasUnreadMessage() throws MessagingException {
        return folder.getUnreadMessageCount() > 0;
    }

    /**
     * 只有在调用 destory() 后才会删除
     * @param messages
     * @throws MessagingException
     */
    protected void delete(Message[] messages) throws MessagingException {
        for (Message message : messages) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
    }

    public void messageCountListener(MessageCountListener messageCountListener) {
        folder.addMessageCountListener(messageCountListener);
        folder.removeMessageCountListener(messageCountListener);
    }

    public void messageCountListener(MessageChangedListener messageChangedListener) {
        folder.addMessageChangedListener(messageChangedListener);
        folder.removeMessageChangedListener(messageChangedListener);
    }

    public Folder getFolder(){
        return this.folder;
    }

    @Override
    public void destroy() {
        // 关闭资源
        if(folder != null || folder.isOpen()){
            try {
                folder.close(expunge);
            } catch (MessagingException e) {
                log.error("---------------JavaMailReceiver folder.close fail:{}---------------", e.getMessage());
            }
        }
        if(store != null || store.isConnected()){
            try {
                store.close();
            } catch (MessagingException e) {
                log.error("---------------JavaMailReceiver store.close fail:{}---------------", e.getMessage());
            }
        }
        log.info("---------------JavaMailReceiver destroy {}---------------", expunge);
    }

    public void destroy(boolean expunge) {
        this.expunge = expunge;
        this.destroy();
    }
}
