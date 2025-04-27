package io.shortcut.utils;

import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class KeyUtil {

    public static final String ALGORITHM = "Ed25519";
    //todo: normally key would need to be stored in a persistent secret service after it is generated to prevent lockdown (or invalidation of all tokens) if the service crashes
    //todo: for mvp reasons however I keep it here
    private PrivateKey privateKey;
    private PublicKey publicKey;


    private void init() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            KeyPair keyPair = keyGen.generateKeyPair();

            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();

        } catch (NoSuchAlgorithmException e) {
            //todo if no algorithm we won't be able to issue jwt tokens so - can't recover, it's a typo-bug
            throw new Error("no encryption algorithm available");
        }
    }

    public PrivateKey getPrivateKey() {
        if (this.privateKey == null) {
            init();
        }
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        if (this.publicKey == null) {
            init();
        }
        return this.publicKey;
    }
}
