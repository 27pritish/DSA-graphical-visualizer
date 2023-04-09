import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BinaryTreeFrame extends JFrame {
    BinaryTreePanel binaryTreePanel;
    JTextField inputField;

    public BinaryTreeFrame() {
        binaryTreePanel = new BinaryTreePanel();
        add(binaryTreePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JLabel inputLabel = new JLabel("Enter a value:");
        inputField = new JTextField(5);
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    int value = Integer.parseInt(input);
                    binaryTreePanel.insert(value);
                    inputField.setText("");
                }
            }
        });

        bottomPanel.add(inputLabel);
        bottomPanel.add(inputField);
        bottomPanel.add(insertButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Binary Tree Visualizer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

class BinaryTreePanel extends JPanel {
    private TreeNode root;
    private int nodeSize = 50;
    private int gap = 30;

    public void insert(int value) {
        root = insert(root, value);
        repaint();
    }

    private TreeNode insert(TreeNode node, int value) {
        if (node == null) {
            node = new TreeNode(value);
        } else if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            drawTree(g, getWidth() / 2, 30, root, nodeSize, gap);
        }
    }

    private void drawTree(Graphics g, int x, int y, TreeNode node, int nodeSize, int gap) {
        g.drawOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
        g.drawString(Integer.toString(node.value), x - 5, y + 5);

        if (node.left != null) {
            int xLeft = x - gap;
            int yLeft = y + nodeSize + gap;
            g.drawLine(x, y + nodeSize / 2, xLeft + nodeSize / 2, yLeft - nodeSize / 2);
            drawTree(g, xLeft, yLeft, node.left, nodeSize, gap / 2);
        }

        if (node.right != null) {
            int xRight = x + gap;
            int yRight = y + nodeSize + gap;
            g.drawLine(x, y + nodeSize / 2, xRight + nodeSize / 2, yRight - nodeSize / 2);
            drawTree(g, xRight, yRight, node.right, nodeSize, gap / 2);
        }
    }

    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }





}

class BubbleSortFrame extends JFrame {
    private JPanel arrayPanel;
    private int[] array = {34, 16, 26, 15, 5, 17, 8, 13, 20, 30};
    private JButton startBtn;
    private boolean isSorting = false;
    private int i = 0, j = 0;



    public BubbleSortFrame() {
        setTitle("Bubble Sort Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the panel to display the array
        arrayPanel = new JPanel();
        arrayPanel.setLayout(new GridLayout(1, array.length));
        arrayPanel.setPreferredSize(new Dimension(700, 50));
        for (int i = 0; i < array.length; i++) {
            JLabel label = new JLabel(String.valueOf(array[i]));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            arrayPanel.add(label);
        }
        add(arrayPanel, BorderLayout.CENTER);

        // Create the start button
        startBtn = new JButton("Start");
        startBtn.setPreferredSize(new Dimension(100, 50));
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSorting) {
                    // Pause sorting
                    isSorting = false;
                    startBtn.setText("Resume");
                } else {
                    // Resume sorting
                    isSorting = true;
                    startBtn.setText("Pause");
                    bubbleSort();
                }
            }
        });
        add(startBtn, BorderLayout.SOUTH);
    }

    private void bubbleSort() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (i < array.length - 1 && isSorting) {
                    j = 0;
                    while (j < array.length - i - 1 && isSorting) {
                        // Highlight the two elements being compared
                        highlight(j, j + 1, Color.RED);
                        // Sleep for 500ms to show the comparison
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        if (array[j] > array[j + 1]) {
                            // Swap the elements
                            int temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                            // Update the display
                            updateArray();
                        }
                        // Remove the highlight
                        highlight(j, j + 1, Color.WHITE);
                        j++;
                    }
                    i++;
                }
            }
        });
        t.start();
    }

    private void highlight(int i, int j, Color color) {
        JLabel label1 = (JLabel) arrayPanel.getComponent(i);
        JLabel label2 = (JLabel) arrayPanel.getComponent(j);
        label1.setBackground(color);
        label2.setBackground(color);
    }

    private void updateArray() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    JLabel label = (JLabel) arrayPanel.getComponent(i);
                    label.setText(String.valueOf(array[i]));
                }
            }
        });
    }
}



class MyFrame extends JFrame implements ActionListener {
    Container c;
    JButton btn;
    JButton btn2;
    JLabel heading;

    MyFrame() {
        c = getContentPane();
        c.setLayout(null);

        heading = new JLabel("Data Structure ");
        heading.setFont(new Font("Serif", Font.BOLD, 24));
        heading.setBounds(200, 50, 400, 50);
        c.add(heading);

        JLabel space = new JLabel("");
        space.setBounds(0, 100, 700, 30);
        c.add(space);

        btn = new JButton("Binary Tree");
        btn.setPreferredSize(new Dimension(120, 50));
        btn.setBounds(100, 150, btn.getPreferredSize().width, btn.getPreferredSize().height);
        c.add(btn);
        btn.addActionListener(this);

        btn2 = new JButton("Bubble Sort");
        btn2.setPreferredSize(new Dimension(120, 50));
        btn2.setBounds(btn.getX() + btn.getWidth() + 20, btn.getY(), btn2.getPreferredSize().width, btn2.getPreferredSize().height);
        c.add(btn2);
        btn2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            BinaryTreeFrame binaryTreeFrame = new BinaryTreeFrame();
            binaryTreeFrame.setSize(800, 600);
            binaryTreeFrame.setVisible(true);
        } else if (e.getSource() == btn2) {
            BubbleSortFrame b = new BubbleSortFrame();
            b.setSize(800, 600);
            b.setVisible(true);
        }
    }
}

class Proj {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.setTitle("Amity Learning Portal");
        f.setSize(700, 500);
        f.setLocation(100, 100);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
