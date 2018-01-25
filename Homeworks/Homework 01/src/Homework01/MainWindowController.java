package Homework01;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainWindowController
{
    // Tabs
    @FXML private TabPane mainWindowTabPane;
    @FXML private Tab homework01Tab;

    // Labels
    @FXML private Label statusLabel;

    // TextAreas
    @FXML private TextArea plaintextTextArea;
    @FXML private TextArea cipherTextArea;
    @FXML private TextField keyTextField;

    // Button
    @FXML private Button encryptButton;

    private String encryptionAlgorithm = "CAESAR";

    public void initialize()
    {
    }

    /**NAVIGATION HANDLERS**/
    public void onGoToProject2()
    {
        mainWindowTabPane.getSelectionModel().select(homework01Tab);
    }

    public void onCaesarSelected()
    {
        encryptionAlgorithm = "CAESAR";
        keyTextField.clear();
        plaintextTextArea.clear();
        cipherTextArea.clear();
        encryptButton.setDisable(false);
    }

    public void onPlayFairSelected()
    {
        encryptionAlgorithm = "PLAYFAIR";
        keyTextField.clear();
        plaintextTextArea.clear();
        cipherTextArea.clear();
        encryptButton.setDisable(false);
    }

    public void onVigenereSelected()
    {
        encryptionAlgorithm = "VIGENERE";
        keyTextField.clear();
        plaintextTextArea.clear();
        cipherTextArea.clear();
        encryptButton.setDisable(false);
    }

    public void onCryptanalysisSelected()
    {
        encryptionAlgorithm = "CRYPTANALYSIS";
        keyTextField.clear();
        plaintextTextArea.clear();
        cipherTextArea.clear();
        encryptButton.setDisable(true);
        cipherTextArea.setText("UZQSOVUOHXMOPVGPOZPEVSGZWSZOPFPESXUDBMETSXAIZ"
                + "VUEPHZHMDZSHZOWSFPAPPDTSVPQUZWYMXUZUHSX" + "EPYEPOPDZSZUFPOMBZWPFUPZHMDJUDTMOHMQ");
    }

    public void onEncrypt()
    {
        String plaintext = plaintextTextArea.getText().trim();
        String key = keyTextField.getText().trim();
        String ciphertext = "";

        if(!plaintext.isEmpty())
        {
            switch (encryptionAlgorithm)
            {
                case "CAESAR":
                    ciphertext = Cryptography.getInstance().caesarCipher(Integer.parseInt(key),
                            plaintext, "ENCRYPT");
                    break;
                case "PLAYFAIR":
                    ciphertext = Cryptography.getInstance().playfairCipher(key,
                            plaintext, "ENCRYPT");
                    break;
                case "VIGENERE":
                    ciphertext = Cryptography.getInstance().vigenereCipher(key,
                            plaintext, "ENCRYPT");
                    break;
            }

            cipherTextArea.setText(ciphertext);
        }
    }

    public void onDecrypt()
    {
        String ciphertext = cipherTextArea.getText().trim();
        String key = keyTextField.getText().trim();
        String plaintext = "";

        if(!ciphertext.isEmpty())
        {
            switch (encryptionAlgorithm)
            {
                case "CAESAR":
                    plaintext = Cryptography.getInstance().caesarCipher(Integer.parseInt(key),
                            ciphertext, "DECRYPT");
                    break;
                case "PLAYFAIR":
                    plaintext = Cryptography.getInstance().playfairCipher(key,
                            ciphertext, "DECRYPT");
                    break;
                case "VIGENERE":
                    plaintext = Cryptography.getInstance().vigenereCipher(key,
                            ciphertext, "DECRYPT");
                    break;
                case "CRYPTANALYSIS":
                    plaintext = Cryptanalysis.getInstance().AnalyzeText(ciphertext);
                    break;
            }

            plaintextTextArea.setText(plaintext);
        }
    }

    /**
     * Displays the status messages located in the status bar.
     * @param type - The type of the status message.
     * @param message - The message to display.
     */
    public void setStatus(String type, String message)
    {
        if(type.equals("ERROR"))
            statusLabel.setStyle("-fx-text-fill: red");
        else
            statusLabel.setStyle("-fx-text-fill: white");

        statusLabel.setText(type + ": " + message);
    }
}
