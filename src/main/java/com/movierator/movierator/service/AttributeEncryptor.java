package com.movierator.movierator.service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {
  private Logger logger = LoggerFactory.getLogger(AttributeConverter.class);
  
  private final Key key;
  private final Cipher cipher;

  public AttributeEncryptor(@Value("${user_sensible_data_aes_encryption_key:#{null}}") String secret) throws Exception {
    if(secret == null) {
      logger.warn("`user_sensible_data_aes_encryption_key` not set! Using default is insecure!");
      secret = "secret-key-11111";
    }

    key = new SecretKeySpec(secret.getBytes(), "AES");
    cipher = Cipher.getInstance("AES");
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    if(attribute == null) {
      return null;
    }

    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if(dbData == null) {
      return null;
    }
    
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
    } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      throw new IllegalStateException(e);
    }
  }
}
