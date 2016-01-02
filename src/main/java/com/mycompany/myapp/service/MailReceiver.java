package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Traveller;
import com.mycompany.myapp.domain.Trip;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.StringUtils;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Process inbound emails
 */
@EnableAutoConfiguration
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class MailReceiver {

    private Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    private TripService tripService;

    private TravellerService travellerService;

    public MailReceiver() {
        logger.error("Created Mailer Recevier");
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public void setTravellerService(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    public void processMessage(MimeMessage mimeMessage) throws IOException, MessagingException, URISyntaxException {
//        logger.debug(String.format("Received email: %s", retrieveMessage(mimeMessage)));
//        logger.debug(String.format("The destination is: %s", retrieveDestination(retrieveMessage(mimeMessage))));
        String messaage = retrieveMessage(mimeMessage);
        String destination = retrieveDestination(messaage);
        List<String> receivers = retrieveAllRecipients(mimeMessage);

        if (!StringUtils.isEmpty(destination)) {
            Trip trip = new Trip();
            trip.setDestination(destination);
            tripService.registerTrip(trip);

            for (String receiver : receivers) {
                Traveller traveller = new Traveller();
                traveller.setEmail(receiver);
                traveller.setTrip(trip);
                travellerService.createTraveller(traveller);
            }

        }
    }


    private List<String> retrieveAllRecipients(MimeMessage mimeMessage) throws MessagingException {
        List<String> receivers = new ArrayList<>();

        receivers.addAll(
                Arrays.stream(mimeMessage.getFrom())
                        .map(p -> p.toString())
                        .collect(Collectors.toList()));

        receivers.addAll(
                Arrays.stream(mimeMessage.getAllRecipients())
                        .map(p -> p.toString())
                        .collect(Collectors.toList()));

        return receivers;
    }

    private String retrieveDestination(String message) {
        StringTokenizer stringTokenizer = new StringTokenizer(message, "\n\r");

        while (stringTokenizer.hasMoreElements()) {
            String token = stringTokenizer.nextToken();
            int start = token.indexOf("destination:");
            if (start>=0) {
                return token.substring(start + "destination:".length());
            }
        }

        return "";
    }
    
    private String retrieveMessage(MimeMessage mimeMessage) throws IOException, MessagingException {
        String result = null;

        Object contentObject = mimeMessage.getContent();
        if(contentObject instanceof Multipart) {
            BodyPart clearTextPart = null;
            BodyPart htmlTextPart = null;
            Multipart content = (Multipart)contentObject;
            int count = content.getCount();
            for(int i=0; i<count; i++)
            {
                BodyPart part =  content.getBodyPart(i);
                if(part.isMimeType("text/plain"))
                {
                    clearTextPart = part;
                    break;
                }
                else if(part.isMimeType("text/html"))
                {
                    htmlTextPart = part;
                }
            }

            if(clearTextPart!=null)
            {
                result = (String) clearTextPart.getContent();
            }
            else if (htmlTextPart!=null)
            {
                String html = (String) htmlTextPart.getContent();
                result = Jsoup.parse(html).text();
            }

        } else if (contentObject instanceof String) {
            // a simple text mimeMessage
            result = (String) contentObject;
        }
        else {
            logger.warn("not a mimeMessage",mimeMessage.toString());
            result = null;
        }

        return result;
    }
 
//    @InboundChannelAdapter("counterChannel")
//    public Integer count() {
//        return this.counter.incrementAndGet();
//    }
//
//    String username = "navnex@gmail.com";
//    String password = "neptun76";
//
//
//    @Bean
//    @InboundChannelAdapter(value = "receiveEmailAdapter", poller = @Poller(fixedDelay = "1000", taskExecutor = "asyncTaskExecutor"))
//    public MessageSource<Message> mailMessageSource(MailReceiver mailReceiver) {
//        MailReceivingMessageSource mailReceivingMessageSource = new MailReceivingMessageSource(mailReceiver);
//        // other setters here
//        return mailReceivingMessageSource;
//    }
//
//    @Bean
//    public MailReceiver imapMailReceiver() {
//        String storeURI = String.format("imaps://[%s]:[%s]@imap.gmail.com/INBOX", username, password);
//        ImapMailReceiver imapMailReceiver = new ImapMailReceiver(storeURI);
//        imapMailReceiver.set
//        // other setters here
//        return imapMailReceiver;
//    }
}
