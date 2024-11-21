package view;

import controller.CaesarController;
import controller.SubstitutionController;
import controller.VigenereController;
import model.SubstitutionCipherModel;
import model.VigenereCipherModel;
import model.CaesarCipherModel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;

public class AppView extends JFrame {

    public AppView() {
        setTitle("Ứng dụng Mã hóa & Giải mã");
        setSize(900, 700);  // Tăng kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Font textFont = new Font("Arial", Font.PLAIN, 15);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);
        Font comboFont = new Font("Arial", Font.PLAIN, 15);

        JTabbedPane tabbedPane = new JTabbedPane();
//----------------------------
        // Tab "Giải thuật cơ bản"
        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Thêm khoảng cách giữa các thành phần

        // Nhập văn bản
        JTextArea inputText = new JTextArea(6, 35); // Tăng kích thước textarea
        inputText.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(inputText);
        JButton encryptButton = new JButton("Mã hóa");
        encryptButton.setFont(buttonFont);
        JButton decryptButton = new JButton("Giải mã");
        decryptButton.setFont(buttonFont);
        JComboBox<String> algorithmBox = new JComboBox<>(new String[]{"--- Thuật toán mã hóa cổ điển ---", "Mã hóa Caesar", "Mã hóa Vigenère", "Mã hóa thay thế", "Mã hóa hoán vị    ", "--- Thuật toán băm cơ bản ---", "MD5", "SHA-1"});
        algorithmBox.setFont(comboFont);

        algorithmBox.setSelectedItem("Mã hóa Caesar");

        // Danh sách các mục không hợp lệ
        java.util.List<String> invalidOptions = java.util.Arrays.asList(
                "--- Thuật toán mã hóa cổ điển ---",
                "--- Thuật toán băm cơ bản ---"
        );
        // Lưu trữ lựa chọn trước đó
        final String[] previousSelection = {null};

        // Tùy chỉnh renderer để đổi màu các mục không hợp lệ
        algorithmBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String item = (String) value;

                if (invalidOptions.contains(item)) {
                    setForeground(Color.BLUE); // Màu xám cho các mục không hợp lệ
                } else {
                    setForeground(Color.BLACK); // Màu đen cho các mục hợp lệ
                }
                return component;
            }
        });

        // Xử lý sự kiện chọn
        algorithmBox.addActionListener(e -> {
            String selectedItem = (String) algorithmBox.getSelectedItem();
            if (invalidOptions.contains(selectedItem)) {
                // Đặt lại lựa chọn trước đó
                algorithmBox.setSelectedItem(previousSelection[0]);

            } else {
                // Cập nhật lựa chọn trước đó
                previousSelection[0] = selectedItem;
            }
        });
//        JComboBox<String> modeBox = new JComboBox<>(new String[]{"Chế độ 1", "Chế độ 2"});
        JTextArea outputText = new JTextArea(6, 35); // Tăng kích thước textarea
        outputText.setFont(textFont);
        outputText.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputText);

        JTextField KeyField = new JTextField(35);
        JButton generateKeyButton = new JButton("Tạo khóa");
        generateKeyButton.setFont(buttonFont);
        JButton inputKeyButton = new JButton("Chọn khóa");
        inputKeyButton.setFont(buttonFont);

        // Nút chọn file
        JButton chooseFileButton = new JButton("Chọn File");
        chooseFileButton.setFont(buttonFont);
        JTextField filePathField = new JTextField(35); // Tăng kích thước JTextField
        filePathField.setEditable(false);

        // nút Copy
        JButton copyButton = new JButton("Sao chép");
        copyButton.setFont(buttonFont);
        gbc.gridx = 2;
        gbc.gridy = 7;
        basicPanel.add(copyButton, gbc);
        copyButton.addActionListener(e -> {
            String textToCopy = outputText.getText(); // Lấy nội dung từ JTextArea
            StringSelection stringSelection = new StringSelection(textToCopy); // Tạo đối tượng StringSelection
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // Lấy clipboard hệ thống
            clipboard.setContents(stringSelection, null); // Sao chép vào clipboard
            JOptionPane.showMessageDialog(this, "Đã sao chép văn bản vào clipboard.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
        // nút xem lại bảng thay thế
        JButton viewMapButton = new JButton("Xem lại bảng");
        viewMapButton.setEnabled(false); // Vô hiệu hóa ban đầu
        gbc.gridx = 1;
        gbc.gridy = 5;
        basicPanel.add(viewMapButton, gbc); // Đặt vị trí phù hợp trên giao diện

        // Căn chỉnh các thành phần
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel algorithmLabel = new JLabel("Chọn thuật toán:");
        algorithmLabel.setFont(labelFont); // Thiết lập font cho JLabel
        basicPanel.add(algorithmLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        basicPanel.add(algorithmBox, gbc);

        // Chọn file
        gbc.gridx = 0;
        gbc.gridy = 1;
        basicPanel.add(chooseFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        basicPanel.add(filePathField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel orTextLabel = new JLabel("Hoặc");
        orTextLabel.setFont(labelFont); // Thiết lập font cho JLabel
        basicPanel.add(orTextLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel inputTextLabel = new JLabel("Nhập văn bản:");
        inputTextLabel.setFont(labelFont); // Thiết lập font cho JLabel
        basicPanel.add(inputTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        basicPanel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel keyLabel = new JLabel("Nhập khóa:");
        keyLabel.setFont(labelFont); // Thiết lập font cho JLabel
        basicPanel.add(keyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        basicPanel.add(KeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        basicPanel.add(generateKeyButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        basicPanel.add(inputKeyButton, gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        basicPanel.add(new JLabel("Chọn chế độ:"), gbc);

//        gbc.gridx = 1;
//        gbc.gridy = 3;
//        basicPanel.add(modeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        basicPanel.add(encryptButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        basicPanel.add(decryptButton, gbc);

        // Output
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel outputTextLabel = new JLabel("Kết quả:");
        outputTextLabel.setFont(labelFont); // Thiết lập font cho JLabel
        basicPanel.add(outputTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        basicPanel.add(outputScrollPane, gbc);

//   -----------------     MA HOA CAESAR     -------------------------------------
        // Khai báo các đối tượng cho từng thuật toán
        CaesarCipherModel caesarModel = new CaesarCipherModel();
        VigenereCipherModel vigenereModel = new VigenereCipherModel();
        SubstitutionCipherModel substitutionModel = new SubstitutionCipherModel();

        CaesarController caesarController = new CaesarController(caesarModel);
        VigenereController vigenereController = new VigenereController(vigenereModel);
        SubstitutionController substitutionController = new SubstitutionController(substitutionModel);

// Xử lý sự kiện chọn thuật toán
        algorithmBox.addActionListener(e -> {
            String selectedItem = (String) algorithmBox.getSelectedItem();

            // Kiểm tra xem người dùng chọn thuật toán nào
            switch (selectedItem) {
                case "Mã hóa Caesar":
                    // Kích hoạt các thành phần UI cho thuật toán Caesar
                    KeyField.setEnabled(true);
                    generateKeyButton.setEnabled(true);
                    inputKeyButton.setEnabled(true);
                    viewMapButton.setEnabled(false);
                    break;
                case "Mã hóa Vigenère":
                    // Kích hoạt các thành phần UI cho thuật toán Vigenère
                    KeyField.setEnabled(true); // Khóa cho Vigenère
                    generateKeyButton.setEnabled(true); // Tạo khóa Vigenère
                    inputKeyButton.setEnabled(true);
                    viewMapButton.setEnabled(false);
                    break;
                case "Mã hóa thay thế":
                    // Kích hoạt các thành phần UI cho thuật toán thay thế
                    KeyField.setEnabled(true);
                    generateKeyButton.setEnabled(true);
                    inputKeyButton.setEnabled(true);
                    break;
                default:
                    // Nếu không chọn thuật toán nào, tắt các thành phần UI
                    KeyField.setEnabled(false);
                    generateKeyButton.setEnabled(false);
                    inputKeyButton.setEnabled(false);
                    break;
            }
        });

// Xử lý sự kiện mã hóa
        encryptButton.addActionListener(e -> {
            String plainText = inputText.getText();
            String key = KeyField.getText();
            String encryptedText = "";

            if (plainText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập văn bản cần mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
            switch (selectedAlgorithm) {
                case "Mã hóa Caesar":
                    try {
                        int caesarKey = Integer.parseInt(key);
                        encryptedText = caesarController.handleEncryption(plainText, caesarKey);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Khóa phải là số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Mã hóa Vigenère":
                    encryptedText = vigenereController.handleEncryption(plainText, key);
                    break;
                case "Mã hóa thay thế":
                    encryptedText = substitutionController.handleEncryption(plainText);
                    break;
            }

            outputText.setText(encryptedText);
            JOptionPane.showMessageDialog(this, "Mã hóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

// Xử lý sự kiện giải mã
        decryptButton.addActionListener(e -> {
            String cipherText = inputText.getText();
            String key = KeyField.getText();
            String decryptedText = "";

            if (cipherText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập văn bản cần giải mã.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();

            switch (selectedAlgorithm) {
                case "Mã hóa Caesar":
                    try {
                        int caesarKey = Integer.parseInt(key);
                        decryptedText = caesarController.handleDecryption(cipherText, caesarKey);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Khóa phải là số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Mã hóa Vigenère":
                    decryptedText = vigenereController.handleDecryption(cipherText, key);
                    break;
                case "Mã hóa thay thế":
                    decryptedText = substitutionController.handleDecryption(cipherText);
                    break;
            }

            outputText.setText(decryptedText);
            JOptionPane.showMessageDialog(this, "Giải mã thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

// Tạo khóa cho các thuật toán
        generateKeyButton.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
            String generatedKey = "";
            Map<Character, Character> substitutionMap;

            try {
                switch (selectedAlgorithm) {
                    case "Mã hóa Caesar":
                        generatedKey = String.valueOf(caesarController.generateKey());
                        break;
                    case "Mã hóa Vigenère":
                        generatedKey = vigenereController.generateKey();
                        break;
                    case "Mã hóa thay thế":
                        substitutionMap = substitutionController.generateRandomSubstitutionMap();
                        StringBuilder substitutionDisplay = new StringBuilder("Bảng thay thế:\n");
                        substitutionMap.forEach((key, value) -> substitutionDisplay.append(key).append(" -> ").append(value).append("\n"));
                        // Hiển thị bảng thay thế
                        JOptionPane.showMessageDialog(this, substitutionDisplay.toString(), "Bảng thay thế", JOptionPane.INFORMATION_MESSAGE);

                        // Lưu bảng thay thế để sử dụng sau
                        substitutionController.setCustomSubstitutionMap(substitutionMap);
                        viewMapButton.setEnabled(true); // Kích hoạt nút "Xem lại bảng"
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuật toán hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Không thể tạo khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KeyField.setText(generatedKey);
        });

// Nhập khóa
        inputKeyButton.addActionListener(e -> {
            String keyInput = KeyField.getText();
            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();

            try {
                switch (selectedAlgorithm) {
                    case "Mã hóa Caesar":
                        caesarController.setKey(keyInput);
                        break;
                    case "Mã hóa Vigenère":
                        if (keyInput.isEmpty()) {
                            throw new IllegalArgumentException("Khóa Vigenère không được để trống.");
                        }
                        vigenereController.setKey(keyInput);
                        break;
                    case "Mã hóa thay thế":
                        substitutionController.setCustomSubstitutionMap(parseSubstitutionMap(keyInput));
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuật toán hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                JOptionPane.showMessageDialog(this, "Khóa đã được thiết lập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

// xem lại bảng
        viewMapButton.addActionListener(e -> {
            Map<Character, Character> substitutionMap = substitutionController.getCurrentSubstitutionMap();

            if (substitutionMap == null || substitutionMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có bảng thay thế được tạo.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder substitutionDisplay = new StringBuilder("Bảng thay thế hiện tại:\n");
            substitutionMap.forEach((key, value) -> substitutionDisplay.append(key).append(" -> ").append(value).append("\n"));

            // Tạo JPanel để hiển thị bảng thay thế và nút sao chép
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea substitutionArea = new JTextArea(substitutionDisplay.toString());
            substitutionArea.setEditable(false);
            substitutionArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

            JScrollPane scrollMapPane = new JScrollPane(substitutionArea);
            panel.add(scrollMapPane, BorderLayout.CENTER);

            // Nút sao chép khoá
            JButton copyMapButton = new JButton("Sao chép khóa");
            copyMapButton.addActionListener(copyEvent -> {
                String substitutionText = substitutionDisplay.toString();
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(substitutionText), null);
                JOptionPane.showMessageDialog(panel, "Khóa đã được sao chép.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            });
            panel.add(copyMapButton, BorderLayout.SOUTH);

            // Hiển thị trong JDialog
            JDialog dialog = new JDialog((Frame) null, "Bảng thay thế", true);
            dialog.getContentPane().add(panel);
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

//-------------------------------
        // Tab "Mã hóa đối xứng"
        JPanel symmetricPanel = new JPanel();
        symmetricPanel.setLayout(new GridBagLayout());

        JTextArea inputSymmetric = new JTextArea(6, 35);
        inputSymmetric.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollSymmetric = new JScrollPane(inputSymmetric);
        JButton encryptSymmetricButton = new JButton("Mã hóa");
        JButton decryptSymmetricButton = new JButton("Giải mã");
        JComboBox<String> symmetricAlgorithmBox = new JComboBox<>(new String[]{"AES", "DES", "Triple DES"});
        JComboBox<String> symmetricModeBox = new JComboBox<>(new String[]{"ECB", "CBC", "CFB", "OFB", "GCM"});
        JTextField symmetricKeyField = new JTextField(35);
        JButton generateSymmetricKeyButton = new JButton("Tạo khóa");
        JButton inputSymmetricKeyButton = new JButton("Nhập khóa");
        JTextArea outputSymmetricText = new JTextArea(6, 35);
        outputSymmetricText.setFont(new Font("Arial", Font.PLAIN, 16));
        outputSymmetricText.setEditable(false);
        JScrollPane outputSymmetricScrollPane = new JScrollPane(outputSymmetricText);

        // Nút chọn file
        JButton chooseSymmetricFileButton = new JButton("Chọn File");
        JTextField symmetricFilePathField = new JTextField(35);
        symmetricFilePathField.setEditable(false);

        // Căn chỉnh các thành phần
        gbc.gridx = 0;
        gbc.gridy = 0;
        symmetricPanel.add(new JLabel("Nhập văn bản:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        symmetricPanel.add(scrollSymmetric, gbc);

        // Chọn file
        gbc.gridx = 0;
        gbc.gridy = 1;
        symmetricPanel.add(chooseSymmetricFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        symmetricPanel.add(symmetricFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        symmetricPanel.add(new JLabel("Chọn thuật toán:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        symmetricPanel.add(symmetricAlgorithmBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        symmetricPanel.add(new JLabel("Chọn chế độ:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        symmetricPanel.add(symmetricModeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        symmetricPanel.add(new JLabel("Nhập khóa:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        symmetricPanel.add(symmetricKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        symmetricPanel.add(generateSymmetricKeyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        symmetricPanel.add(inputSymmetricKeyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        symmetricPanel.add(encryptSymmetricButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        symmetricPanel.add(decryptSymmetricButton, gbc);

        // Output
        gbc.gridx = 0;
        gbc.gridy = 7;
        symmetricPanel.add(new JLabel("Kết quả:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        symmetricPanel.add(outputSymmetricScrollPane, gbc);
//----------------------------------------------------------------------
        // Tab "Mã hóa bất đối xứng"
        JPanel asymmetricPanel = new JPanel();
        asymmetricPanel.setLayout(new GridBagLayout());

        JTextArea inputAsymmetric = new JTextArea(6, 35);
        inputAsymmetric.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollAsymmetric = new JScrollPane(inputAsymmetric);
        JButton encryptAsymmetricButton = new JButton("Mã hóa");
        JButton decryptAsymmetricButton = new JButton("Giải mã");
        JComboBox<String> asymmetricAlgorithmBox = new JComboBox<>(new String[]{"RSA", "ECC"});
        JTextField asymmetricKeyField = new JTextField(35);
        JButton generateAsymmetricKeyButton = new JButton("Tạo khóa");
        JButton inputAsymmetricKeyButton = new JButton("Nhập khóa");
        JTextArea outputAsymmetricText = new JTextArea(6, 35);
        outputAsymmetricText.setFont(new Font("Arial", Font.PLAIN, 16));
        outputAsymmetricText.setEditable(false);
        JScrollPane outputAsymmetricScrollPane = new JScrollPane(outputAsymmetricText);

        // Nút chọn file
        JButton chooseAsymmetricFileButton = new JButton("Chọn File");
        JTextField asymmetricFilePathField = new JTextField(35);
        asymmetricFilePathField.setEditable(false);

        // Căn chỉnh các thành phần
        gbc.gridx = 0;
        gbc.gridy = 0;
        asymmetricPanel.add(new JLabel("Nhập văn bản:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        asymmetricPanel.add(scrollAsymmetric, gbc);

        // Chọn file
        gbc.gridx = 0;
        gbc.gridy = 1;
        asymmetricPanel.add(chooseAsymmetricFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        asymmetricPanel.add(asymmetricFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        asymmetricPanel.add(new JLabel("Chọn thuật toán:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        asymmetricPanel.add(asymmetricAlgorithmBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        asymmetricPanel.add(new JLabel("Nhập khóa:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        asymmetricPanel.add(asymmetricKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        asymmetricPanel.add(generateAsymmetricKeyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        asymmetricPanel.add(inputAsymmetricKeyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        asymmetricPanel.add(encryptAsymmetricButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        asymmetricPanel.add(decryptAsymmetricButton, gbc);

        // Output
        gbc.gridx = 0;
        gbc.gridy = 6;
        asymmetricPanel.add(new JLabel("Kết quả:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        asymmetricPanel.add(outputAsymmetricScrollPane, gbc);
//--------------------------------------------------------------------
        // Tab "Chữ ký điện tử"
        JPanel signaturePanel = new JPanel();
        signaturePanel.setLayout(new GridBagLayout());

        JTextArea inputSignature = new JTextArea(6, 35);
        inputSignature.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollSignature = new JScrollPane(inputSignature);
        JButton createSignatureButton = new JButton("Tạo chữ ký");
        JButton verifySignatureButton = new JButton("Xác minh chữ ký");
        JComboBox<String> signatureAlgorithmBox = new JComboBox<>(new String[]{"SHA-256", "SHA-512"});
        JTextArea outputSignatureText = new JTextArea(6, 35);
        outputSignatureText.setFont(new Font("Arial", Font.PLAIN, 16));
        outputSignatureText.setEditable(false);
        JScrollPane outputSignatureScrollPane = new JScrollPane(outputSignatureText);


        // Nút chọn file
        JButton chooseSignatureFileButton = new JButton("Chọn File");
        JTextField signatureFilePathField = new JTextField(35);
        signatureFilePathField.setEditable(false);

        // Căn chỉnh các thành phần
        gbc.gridx = 0;
        gbc.gridy = 0;
        signaturePanel.add(new JLabel("Nhập văn bản:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        signaturePanel.add(scrollSignature, gbc);

        // Chọn file
        gbc.gridx = 0;
        gbc.gridy = 1;
        signaturePanel.add(chooseSignatureFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        signaturePanel.add(signatureFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signaturePanel.add(new JLabel("Chọn thuật toán chữ ký:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        signaturePanel.add(signatureAlgorithmBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        signaturePanel.add(createSignatureButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        signaturePanel.add(verifySignatureButton, gbc);

        // Output
        gbc.gridx = 0;
        gbc.gridy = 4;
        signaturePanel.add(new JLabel("Kết quả:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        signaturePanel.add(outputSignatureScrollPane, gbc);

        // Thêm các tab vào tabbedPane
        tabbedPane.addTab("Giải thuật cơ bản", basicPanel);
        tabbedPane.addTab("Mã hóa đối xứng", symmetricPanel);
        tabbedPane.addTab("Mã hóa bất đối xứng", asymmetricPanel);
        tabbedPane.addTab("Chữ ký điện tử", signaturePanel);

        add(tabbedPane, BorderLayout.CENTER);
    }


    private Map<Character, Character> parseSubstitutionMap(String keyInput) {
        Map<Character, Character> map = new HashMap<>();
        String[] mappings = keyInput.split(",");
        for (String mapping : mappings) {
            String[] pair = mapping.trim().split("->");
            if (pair.length != 2 || pair[0].length() != 1 || pair[1].length() != 1) {
                throw new IllegalArgumentException("Bảng thay thế không hợp lệ. Định dạng: A->B,C->D,...");
            }
            map.put(pair[0].charAt(0), pair[1].charAt(0));
        }

        return map;
    }
}
