package konsoleFm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GuiFM extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;
    private JList<String> fileList;
    private JButton copyButton;
    private JButton deleteButton;
    private JScrollPane fileListPane;
    private JScrollPane wievPane;
    private JLabel imageLabel;
    private JPanel viewPanel;

    // Модель списка
    private DefaultListModel<String> dlm;
    private Path currentFile;
    private Path[] masFilesName;

    public GuiFM() {
        setContentPane(contentPane);
        setModal(true);
        Dimension dim = new Dimension(600, 400);
        setTitle("FileManager");
        setMinimumSize(dim);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Подключение слушателя мыши
        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { //два клки мишкою
                    // Получение элемента
                    int selected = fileList.locationToIndex(e.getPoint());
                    System.out.println(dlm.getElementAt(selected));
                    // System.out.println(currentFile);
                    // встановлення жля блоків відображення значень за замовчуванням
                    imageLabel.setText(" no image");
                    imageLabel.setIcon(null);
                    textArea.setText("no open file");

                    if (dlm.getElementAt(selected).equals("..")) {  //  якщо символ поверненя на рівень вверх
                        try {
                            currentFile = NioFileComands.myCDreturn(currentFile);
                            setListFiles(currentFile);
                        } catch (Exception ex) {
                            System.out.println("access error");
                        }
                    } else {  // якщо ініші символи
                        try {
                            //File newFile = new File(currentFile.getAbsolutePath() + "\\" + dlm.getElementAt(selected));
                            Path newPath = masFilesName[selected - 1];

                            if (Files.isDirectory(newPath)) {  // якщо пнове посилання є папкою , то перохидомо до неї
                                setListFiles(newPath);
                                currentFile = newPath;

                            } else {
                                String mimeType = Files.probeContentType(newPath);
                                // Pattern p = Pattern.compile(".+\\.txt$"); // тествовий файл будемо вивиди на екран.
                                //  Matcher m = p.matcher(currentFile.getFileName().toString());
                                //   Files.
                                if (mimeType == null) {
                                    textArea.setText("illegal format of file");
                                    return;
                                }

                                System.out.println(mimeType);
                                if (mimeType.equals("text/plain")) {
                                    System.out.println("txt");
                                    String text = "";
                                    try (BufferedReader fis = new BufferedReader(new FileReader(newPath.toFile()))) {
                                        text = "";
                                        String firs;
                                        while ((firs = fis.readLine()) != null) {
                                            text = text + firs + "\n";
                                        }
                                        //  viewPanel.add(wievPane);
                                        // viewPanel.revalidate();
                                        textArea.setText(text);
                                    } catch (IOException ex) {
                                        textArea.setText("error reading file");
                                    }
                                }
                                if (mimeType.equals("image/png") || mimeType.equals("image/jpg")) {
                                    System.out.println("image");
                                    try {
                                        Image img = ImageIO.read(newPath.toFile());
                                        int h = ((BufferedImage) img).getHeight();
                                        System.out.println(h);
                                        int w = ((BufferedImage) img).getWidth();
                                        System.out.println(w);
                                        float ratio = (float) h / (float) w;
                                        System.out.println(ratio);
                                        System.out.println(200 * ratio);
                                        if (w > 200) {
                                            img = img.getScaledInstance(200, (int) (200 * ratio), 1);
                                            imageLabel.setText("");
                                        }
                                        imageLabel.setIcon(new ImageIcon(img));
                                    } catch (IOException ex) {
                                        imageLabel.setText("image load problem");
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "access error");
                            setListFiles(currentFile);
                        }

                    }
                }
            }
        });

      /*  copyButton.addActionListener(new ActionListener() {
            // копіювання файлу
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileList.isSelectionEmpty()) {
                    return;
                }
                String selectFileName = fileList.getSelectedValue();
                File selectFilePath = new File(currentFile.getAbsolutePath() + "\\" + selectFileName);
                if (selectFilePath.isFile()) {
                    System.out.println("file copy");
                    try (FileInputStream fis = new FileInputStream(selectFilePath);
                         FileOutputStream fos = new FileOutputStream(new File(currentFile.getAbsolutePath() + "\\" + "copy" + selectFileName))) {
                        int c;
                        while ((c = fis.read()) != -1) {
                            fos.write(c);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "access error");
                        System.out.println("error");
                    }
                    setListFiles(currentFile);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            // видалення файлу
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileList.isSelectionEmpty()) {
                    return;
                }
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "A you sure wont  to delete file  ?", "Warning", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    String selectFileName = fileList.getSelectedValue();
                    try {
                        File selectFilePath = new File(currentFile.getAbsolutePath() + "\\" + selectFileName);
                        boolean delete = selectFilePath.delete();
                        if (!delete) {
                            JOptionPane.showMessageDialog(null, "delete error");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "access error");
                        System.out.println("error");
                    }
                    setListFiles(currentFile);
                }
            }
        });
        */
    }

    public static void main(String[] args) {

        GuiFM dialog = new GuiFM();
        File file = new File("");
        Path path = file.toPath().toAbsolutePath();
        dialog.setListFiles(path);
        dialog.currentFile = path;
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        dlm = new DefaultListModel<>();
        fileList = new JList<>(dlm);// TODO: place custom component creation code here
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    // оновлення даних в списку
    private void setListFiles(Path path) {

        masFilesName = NioFileComands.myDIRallPath(path, false);
        dlm.removeAllElements();
        for (int i = masFilesName.length - 1; i >= 0; i--) {
            dlm.add(0, masFilesName[i].getFileName().toString());
        }
        dlm.add(0, "..");
        fileList.setModel(dlm);
    }
}
