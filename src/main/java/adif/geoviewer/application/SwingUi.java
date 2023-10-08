package adif.geoviewer.application;

import java.io.File;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import adif.geoviewer.processor.model.Parameters;

public class SwingUi {

    private JFrame frame;
    private JTextField inputPathsField;
    private JTextField outputField;
    private JButton runButton;

    public SwingUi () {
        create();
    }

    private void create() {
        // window
        frame = new JFrame();
        frame.setSize(800, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // input paths 
        JPanel inputPathsPanel = new JPanel();
        setFlowlayoutAlignement(inputPathsPanel, FlowLayout.LEFT);
        frame.add(inputPathsPanel);

        inputPathsPanel.add(new JLabel("Lookup paths: "));
        inputPathsField = new JTextField(50);
        inputPathsPanel.add(inputPathsField);
        inputPathsField.setToolTipText("You can add multiple paths to scan for JPEG files separated by the path separator » " + File.pathSeparatorChar +" «");

        // output file
        JPanel outputPanel = new JPanel();
        setFlowlayoutAlignement(outputPanel, FlowLayout.LEFT);
        frame.add(outputPanel);

        outputPanel.add(new JLabel("Write to: "));
        outputField = new JTextField(50);
        outputPanel.add(outputField);
        JButton outputFileChooserBtn = new JButton("Save as...");
        outputPanel.add(outputFileChooserBtn);

        JFileChooser outputFileChooser = new JFileChooser();
        outputFileChooser.setDialogTitle("Save GPX as...");
        outputFileChooser.setFileFilter(new FileNameExtensionFilter("GPX files (.gpx)", "gpx"));
        outputFileChooserBtn.addActionListener(openOutputFileChooser(outputFileChooser));

        // button
        JPanel buttonPanel = new JPanel();
        setFlowlayoutAlignement(buttonPanel, FlowLayout.RIGHT);
        frame.add(buttonPanel);

        runButton = new JButton("Run");
        buttonPanel.add(runButton);

    }

    void show() {
        frame.setVisible(true);
    }

    void setOnRunListener(Consumer<Parameters> onRunListener) {
        runButton.addActionListener(event -> onRunListener.accept(Parameters.builder()
            .pathsSeparatedByFileSeparator(inputPathsField.getText())
            .outputGpxFile(outputField.getText())
            .build()));
    }

    private ActionListener openOutputFileChooser(JFileChooser outputFileChooser) {
        return event -> {
            int result = outputFileChooser.showSaveDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION) {
                outputField.setText(outputFileChooser.getSelectedFile().getAbsolutePath());
            }
        };
    }

    private void setFlowlayoutAlignement(Container container, int layout) {
        ((FlowLayout)container.getLayout()).setAlignment(layout);
        ((FlowLayout)container.getLayout()).setHgap(20);
    }
}
