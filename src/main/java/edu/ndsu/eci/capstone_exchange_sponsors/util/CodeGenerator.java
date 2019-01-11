package edu.ndsu.eci.capstone_exchange_sponsors.util;

import java.security.SecureRandom;

import org.apache.cayenne.ObjectContext;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;

/**
 * Generates non-confusing codes that are 8 digits long.
 */
public class CodeGenerator {

  /**Non-confusing lower case alphabet and numbers, 29 characters */
  private static final char[] CUSTOM_ALPHABET = {
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 
      'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 
      't', 'w', 'x', 'y', 'z',
      '2', '3', '4', '5', '6', '7', '8', '9',
      };
  
  /** The length of the code generated */
  private static final int CODE_SIZE = 8;
  
  /**
   * Generates a unique 8 digit code for Site objects.
   * @param context Cayenne database context.
   * @return Generated code.
   */
  public String getCode(ObjectContext context) {
    CapstoneDomainMap map = CapstoneDomainMap.getInstance();
    StringBuilder code;
    SecureRandom random = new SecureRandom();
    
    //Keep generating till a unique match is made, should have 29^8 (a bit over 500 billion) options to pick from
    do {
      code = new StringBuilder();
      for(int i = 0; i < CODE_SIZE; i++) {
        code.append(CUSTOM_ALPHABET[random.nextInt(CUSTOM_ALPHABET.length)]);
      }
    } while (!map.performSiteByCodeQuery(context, code.toString()).isEmpty());
    
    return code.toString();
  }
  
}
