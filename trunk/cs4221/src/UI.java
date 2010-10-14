
import javax.swing.*;
import java.awt.*;
import java.awt.Container.*;
import java.awt.Panel.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class UI extends JFrame implements ActionListener {

    public UI() {
        initComponents();
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        button7 = new JButton();
        buttons = new JButton();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        scrollPane3 = new JScrollPane();
        textArea3 = new JTextArea();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Input");
        label1.setFont(new Font("Tahoma", Font.PLAIN, 14));

        //---- label2 ----
        label2.setText("Output");
        label2.setFont(new Font("Tahoma", Font.PLAIN, 14));

        //---- button1 ----
        button1.setText("Step 1");
        button1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button1.addActionListener(this);

        //---- button2 ----
        button2.setText("Step 2");
        button2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button2.addActionListener(this);
        button2.setEnabled(false);

        //---- button3 ----
        button3.setText("Step 3");
        button3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button3.addActionListener(this);
        button3.setEnabled(false);

        //---- button4 ----
        button4.setText("Step 4");
        button4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button4.addActionListener(this);
        button4.setEnabled(false);

        //---- button5 ----
        button5.setText("Step 5");
        button5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button5.addActionListener(this);
        button5.setEnabled(false);

        //---- button6 ----
        button6.setText("Step 6");
        button6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button6.addActionListener(this);
        button6.setEnabled(false);

        //---- button7 ----
        button7.setText("Exit");
        button7.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button7.setForeground(Color.blue);
        button7.addActionListener(this);

        //---- buttons ----
        buttons.setText("Step S");
        buttons.setFont(new Font("Tahoma", Font.PLAIN, 14));
        buttons.addActionListener(this);
        buttons.setEnabled(false);

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea1.setForeground(Color.blue);
            textArea1.setBackground(new Color(255, 255, 153));
            textArea1.setText("F={A->B,A->C,B->C,B->D,D->B,ABE->F}");
            scrollPane1.setViewportView(textArea1);
        }

        //======== scrollPane2 ========
        {

            //---- textArea2 ----
            textArea2.setFont(new Font("Monospaced", Font.PLAIN, 12));
            scrollPane2.setViewportView(textArea2);
        }

        //======== scrollPane3 ========
        {

            //---- textArea3 ----
            textArea3.setText("@Author\nAng Guo Zheng\nCui Zheng\nMuhd Ismail\nYap Hoong Kwan");
            textArea3.setFont(new Font("Monospaced", Font.PLAIN, 10));
            textArea3.setEditable(false);
            textArea3.setEnabled(true);
            scrollPane3.setViewportView(textArea3);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup().addContainerGap().addGroup(contentPaneLayout.createParallelGroup().addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE).addGroup(contentPaneLayout.createSequentialGroup().addGroup(contentPaneLayout.createParallelGroup().addComponent(label1).addComponent(label2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)).addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)).addGroup(contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE).addComponent(button7).addContainerGap()).addGroup(contentPaneLayout.createSequentialGroup().addGap(18, 18, 18).addGroup(contentPaneLayout.createParallelGroup().addComponent(button1).addComponent(button2).addComponent(button3).addComponent(button4).addComponent(button5).addComponent(button6).addComponent(buttons).addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()))));
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup().addContainerGap().addGroup(contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup().addComponent(label1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(label2)).addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup().addComponent(button1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(buttons).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE).addComponent(button7)).addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JLabel label1;
    private JLabel label2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton buttons;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JScrollPane scrollPane3;
    private JTextArea textArea3;
    private String Output = "";
    private FDSet fdset;
    private BigSet bigset;
    private String[][] arrayJ;
    private Transitivity t;
    private BigSet bigset2;
    private FDAlgorithms a;

    public void actionPerformed(ActionEvent event) {

        if (event.getSource().equals(button1)) {
            try {
                int maxFD = 10, width = 2;

                ///Step 0 : Read the input and put it into a FDSet::fdset
                String input = textArea1.getText();
                this.Output += input;

                ReadInput readInput = new ReadInput();
                String[][] fds = new String[maxFD][width];
                fds = readInput.readInput(input, maxFD, width);


                fdset = new FDSet(fds, maxFD);
                this.Output += "\nThis is the original FDSet with the FDs\n";
                Output += fdset.toString();
                FDSet temp = fdset;
                ///Step 1: Eliminate extraneous attributes
                // fdset=FDAlgorithms.allSingleRHSAttribute(fdset);
                fdset = FDAlgorithms.removeExtraneousAttr(fdset);
                this.Output += "\nThis is after step 1\n";
                this.Output += fdset.toString();

                this.button2.setEnabled(true);
                this.button1.setEnabled(false);

                this.textArea2.setText(this.Output);
                this.textArea1.setEditable(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button2)) {
            try {
                ///Step 2:
                fdset = FDAlgorithms.removeRedundantFDs(fdset);
                this.Output += "\nThis is the minimal cover after step 1 and 2\n";
                Output += fdset.toString();

                this.button3.setEnabled(true);
                this.button2.setEnabled(false);

                this.textArea2.setText(this.Output);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button3)) {
            try {
                ///Step 3 Partition : Convert the FDSet into a few FDSets(BigSet)
                Partition p = new Partition();
                bigset = p.works(fdset);
                this.Output += "\nThis is the BigSet after partiton result\n";
                this.Output += bigset.toString();

                this.button4.setEnabled(true);
                this.button3.setEnabled(false);

                this.textArea2.setText(this.Output);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button4)) {
            try {
                Merge m = new Merge();
                bigset = m.works(bigset, fdset);
                arrayJ = m.getArrayJ();
                this.Output += ("\nThis is the BigSet after merge result\n");
                this.Output += bigset.toString();

                this.Output += ("\nthis is the FDSet J\n");
                this.Output += m.getJ().toString();

                this.button5.setEnabled(true);
                this.button4.setEnabled(false);

                this.textArea2.setText(this.Output);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button5)) {
            try {
                String[][] data = bigset.BigSetToArray(bigset.countFD());
                this.t = new Transitivity(data);
                data = t.m5_plotTransitivity();
                t.m5_indentifyTransitivity(data, arrayJ);
                this.Output += ("\nThis is the BigSet after TD elimination result\n");
                this.Output += t.m5_getTDvar();
                this.Output += t.m5_toString();

                this.button6.setEnabled(true);
                this.button5.setEnabled(false);

                this.textArea2.setText(this.Output);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button6)) {
            try {
                this.Output += ("\nThere u have the Relations\n");
                this.Output += t.m6_toString();

                bigset2 = t.m6_displayRelation();
                this.buttons.setEnabled(true);
                this.button6.setEnabled(false);

                this.textArea2.setText(this.Output);
                this.textArea1.setEditable(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(buttons)) {
            try {
                this.Output += ("\nRemove Superfluous attribute\n");
                LinkedList ll = new LinkedList();
                ll = FDAlgorithms.removeSuperfluous(bigset2).BigSetToRelation();
                this.Output += FDAlgorithms.superfluous_tostring(ll);


                this.button1.setEnabled(true);
                this.buttons.setEnabled(false);

                this.textArea2.setText(this.Output);
                this.textArea1.setEditable(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        } else if (event.getSource().equals(button7)) {
            //Exit the program
            System.exit(0);
        }

    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        UI ui = new UI();
        ui.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}
