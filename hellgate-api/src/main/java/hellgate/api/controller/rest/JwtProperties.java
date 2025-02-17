package hellgate.api.controller.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Getter
@Setter
@ToString
@ConfigurationProperties("jwt")
public class JwtProperties {

    private static final Duration DEF_EXPIRY = Duration.ofSeconds(36000);

    /**
     * 公钥。
     *
     * 现有私钥，再根据私钥生成公钥：
     *
     * {@code openssl rsa -in app.key -pubout -outform PEM -out app.pub }
     */
    private RSAPublicKey publicKey;
    /**
     * 私钥。
     * <p>
     * 在 ubuntu 中，可以使用命令生成私钥：
     *
     * {@code openssl genrsa 2048 | openssl pkcs8 -topk8 -nocrypt -out app.key }
     */
    private RSAPrivateKey privateKey;

    private Duration expiry = DEF_EXPIRY;
}
