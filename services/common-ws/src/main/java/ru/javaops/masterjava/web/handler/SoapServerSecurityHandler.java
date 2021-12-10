package ru.javaops.masterjava.web.handler;

import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.typesafe.config.Config;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.config.Configs;
import ru.javaops.masterjava.web.AuthUtil;

import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class SoapServerSecurityHandler extends SoapBaseHandler {

   /* private final static SoapServerSecurityHandler INSTANCE =
            new SoapServerSecurityHandler(Configs.getConfig("hosts.conf", "hosts.mail"));*/
    private static final Config SECURITY_CONFIG = Configs.getConfig("hosts.conf", "hosts.mail");

    private final String user;
    private final String password;
    private final String authHeader;

    public SoapServerSecurityHandler() {
        user = SECURITY_CONFIG.getString("user");
        password = SECURITY_CONFIG.getString("password");
        authHeader = AuthUtil.encodeBasicAuthHeader(user, password);
    }

    /*public static SoapServerSecurityHandler getInstance() {
        return INSTANCE;
    }*/

    @Override
    public boolean handleMessage(MessageHandlerContext context) {
        Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
        int code = AuthUtil.checkBasicAuth(headers, authHeader);
        if (code != 0) {
            context.put(MessageContext.HTTP_RESPONSE_CODE, code);
            handleFault(context);
            throw new SecurityException();
        }
        log.info("Authorisation success");
        return true;
    }

    @Override
    public boolean handleFault(MessageHandlerContext context) {
        log.error("Authorisation faulted");
        return true;
    }

    /*public static class ClientSecurityHandler extends SoapServerSecurityHandler {

        public ClientSecurityHandler(Config conf) {
            super(conf);
        }
    }*/

    /*public static class ServerSecurityHandler extends SoapServerSecurityHandler {

        private static final Config config = Configs.getConfig("hosts.conf", "hosts.mail");

        public ServerSecurityHandler() {
            super(config);
        }
    }*/
}
