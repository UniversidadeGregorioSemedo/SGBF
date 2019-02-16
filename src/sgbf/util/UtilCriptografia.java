package sgbf.util;

import javafx.scene.control.Alert;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * REFERÊNCIA DO CÓDIGO ESTÁ ABAIXO:
 * https://www.devmedia.com.br/utilizando-criptografia-simetrica-em-java/31170
 *
 * @author Dell
 */
public class UtilCriptografia {

    private static String IV = "AAAAAAAAAAAAAAAA";
    private static String chaveencriptacao = "0123456789abcdef";

    public static String encrypt(String textopuro, String operacao) {
        try {
            Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
            encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
            byte[] textoEncriptado = encripta.doFinal(textopuro.getBytes("UTF-8"));
            return new BASE64Encoder().encode(textoEncriptado);
        } catch (Exception erro) {
            throw new UtilControloExcessao(operacao, "Erro ao encriptar dados"
                    + "\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static String decrypt(String textoencriptado, String operacao) {
        try {
            Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
            decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
            byte[] textoDecriptadoBase64 = new BASE64Decoder().decodeBuffer(textoencriptado);
            byte[] textoDecripatoAes = decripta.doFinal(textoDecriptadoBase64);
            return new String(textoDecripatoAes, "UTF-8");
        } catch (Exception erro) {
            throw new UtilControloExcessao(operacao, "Erro ao decriptar dados"
                    + "\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
