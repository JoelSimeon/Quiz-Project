
package quickquiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;


/*
 * @author Joel Simeon
 */
public class QuickQuiz extends JFrame implements ActionListener, WindowListener {

    int QuestionDataSize = 100;
    
    QuestionData[] questionInfo = new QuestionData[QuestionDataSize];
    
    ArrayList<Object[]> dataValues;
    
    String questionNumber = "";
    
    public String fileName = "QuickQuizQuestions.txt";
    
    Color backgroundcolor = new Color(114, 166, 249);
    Color labelcolor = new Color(10, 150, 77);
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    
    JLabel lblQuickQuiz, lblRightTopic, lblQuestion, lblSortBy, lblCorrectAns, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder, lblNumberQuestion, lblA, lblB, lblC, lblD;
    JTextArea txtRightTopic, txtQuestion, txtA, txtB, txtC, txtD, txtCorrectAns, txtLinkedList, txtBinaryTree;
    JButton btnExit, btnSortQn, btnSortTopic, btnSortQuestion, btnSend, btnDisplayBinaryTree, btnPreOrderDisplay, btnPreOrderSave, btnInOrderDisplay, btnInOrderSave, btnPostOrderDisplay, btnPostOrderSave;
    JTable table;
    MyModel wordModel;
    
    int maxEntries = 100;     
    int totalQuestions = 0;
    
    public static void main(String[] args) {
        QuickQuiz quickquizzapplication = new QuickQuiz();
        quickquizzapplication.DisplayGUI();
    }

    public void DisplayGUI() {
        setSize(900,800);
        setLocation(50,50);
        setTitle("Quick Quiz");
        setResizable(false);
        this.getContentPane().setBackground(backgroundcolor);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        LocateLabels(springLayout);
        LocateButtons(springLayout);
        LocateJTextAreas(springLayout);
        QuestionTable(springLayout);
        readFile(fileName);
        setVisible(true);
        
        
        
    }
    
    public void LocateLabels(SpringLayout myLabelLayout)
    {
        lblQuickQuiz = LocateALabel(myLabelLayout, lblQuickQuiz, "Quick Quiz", 400, 20);
        lblRightTopic = LocateALabel(myLabelLayout, lblRightTopic, "Topic:", 570, 60);
        lblQuestion = LocateALabel(myLabelLayout, lblQuestion, "Question:", 550, 100);
        lblNumberQuestion = LocateALabel (myLabelLayout, lblNumberQuestion, "", 590, 120);
        lblSortBy = LocateALabel(myLabelLayout, lblSortBy, "Sort By:", 10, 380);
        lblCorrectAns = LocateALabel(myLabelLayout, lblCorrectAns, "Correct Ans:", 535, 380);
        lblLinkedList = LocateALabel(myLabelLayout, lblLinkedList, "Linked List:", 10, 430);
        lblBinaryTree = LocateALabel(myLabelLayout, lblBinaryTree, "Binary Tree:", 10, 540);
        lblPreOrder = LocateALabel(myLabelLayout, lblPreOrder, "Pre-Order", 67, 640);
        lblInOrder = LocateALabel(myLabelLayout, lblInOrder, "In-Order", 420, 640);
        lblPostOrder = LocateALabel(myLabelLayout, lblPostOrder, "Post-Order", 768, 640);
        lblA = LocateALabel(myLabelLayout, lblA, "A:", 595, 200);
        lblB = LocateALabel(myLabelLayout, lblB, "B:", 595, 245);
        lblC = LocateALabel(myLabelLayout, lblC, "C:", 595, 290);
        lblD = LocateALabel(myLabelLayout, lblD, "D:", 595, 335);
    }
    
    public JLabel LocateALabel(SpringLayout myLabelLayout, JLabel myJLabel, String  JLabelCaption, int x, int y)
    {
        myJLabel = new JLabel(JLabelCaption);
        add(myJLabel); 
        myLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, this);
        myLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, this);
        return myJLabel;
    }

    public void LocateButtons(SpringLayout myButtonLayout)
    {
        btnExit = LocateAButton(myButtonLayout, btnExit, "Exit", 800, 735, 80, 25);
        btnSortQn = LocateAButton(myButtonLayout, btnSortQn, "Qn #", 65, 376, 80, 25);
        btnSend = LocateAButton(myButtonLayout, btnSend, "Send", 735, 376, 150, 25);
        btnSortTopic = LocateAButton(myButtonLayout, btnSortTopic, "Topic", 150, 376, 80, 25);
        btnSortQuestion = LocateAButton(myButtonLayout, btnSortQuestion, "Question", 235, 376, 85, 25);
        //btnDisplayBinaryTree = LocateAButton(myButtonLayout, btnDisplayBinaryTree, "DisplayB", 320, 500, 80, 25);
        btnPreOrderDisplay = LocateAButton(myButtonLayout, btnPreOrderDisplay, "Display", 10, 660, 80, 25);
        btnPreOrderSave = LocateAButton(myButtonLayout, btnPreOrderSave, "Save", 95, 660, 80, 25);
        btnInOrderDisplay = LocateAButton(myButtonLayout, btnInOrderDisplay, "Display", 360, 660, 80, 25);
        btnInOrderSave = LocateAButton(myButtonLayout, btnInOrderSave, "Save", 445, 660, 80, 25);
        btnPostOrderDisplay = LocateAButton(myButtonLayout, btnPostOrderDisplay, "Display", 717, 660, 80, 25);
        btnPostOrderSave = LocateAButton(myButtonLayout, btnPostOrderSave, "Save", 802, 660, 80, 25);
    }
    
    public JButton LocateAButton(SpringLayout myButtonLayout, JButton myButton, String  ButtonCaption, int x, int y, int w, int h)
    {    
        myButton = new JButton(ButtonCaption);
        add(myButton);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
        return myButton;
    }
    
    public void LocateJTextAreas(SpringLayout myTextLayout)
    {
        txtQuestion = LocateAJTextArea(myTextLayout, txtQuestion, 620, 100, 4, 23);
        txtRightTopic = LocateAJTextArea(myTextLayout, txtRightTopic, 620, 55, 1, 23);
        txtA = LocateAJTextArea(myTextLayout, txtA, 620, 195, 1, 23);
        txtB = LocateAJTextArea(myTextLayout, txtB, 620, 240, 1, 23);
        txtC = LocateAJTextArea(myTextLayout, txtC, 620, 285, 1, 23);
        txtD = LocateAJTextArea(myTextLayout, txtD, 620, 330, 1, 23);
        txtCorrectAns = LocateAJTextArea (myTextLayout, txtCorrectAns, 620, 375, 1, 5);
        txtLinkedList = LocateAJTextArea (myTextLayout, txtLinkedList, 10, 450, 3, 78);
        txtBinaryTree = LocateAJTextArea (myTextLayout, txtBinaryTree, 10, 560, 3, 78);
    }

    public JTextArea LocateAJTextArea(SpringLayout myTextLayout, JTextArea myJTextArea, int x, int y, int w, int h)
    {   
        myJTextArea = new JTextArea(w,h);
        add(myJTextArea);
        myJTextArea.setLineWrap(true);
        myJTextArea.setBorder(BorderFactory.createCompoundBorder(border,
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        myJTextArea.setEditable(true);
        myTextLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, this);
        myTextLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, this);
        return myJTextArea;
    }
    
    public void readFile (String fileName)
    {
        try
        {
            FileInputStream fStream = new FileInputStream(fileName);
            DataInputStream inStream = new DataInputStream(fStream);
            BufferedReader brRead = new BufferedReader (new InputStreamReader(inStream));
            
            int i = 0;
            String line;
            
            while ((line = brRead.readLine()) !=null)
            {
                String[] temp = line.split(",");
                questionInfo[i] = new QuestionData(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7] ) ;
                dataValues.add(new Object[] {temp[0],temp[1],temp[2]});
                i++;
            }
            totalQuestions = i;
            fStream.close();
            inStream.close();
            brRead.close();
        }
        catch (Exception e)
        {
            System.err.println("Error Reading File: " + e.getMessage());      
        }
    }
    
    public void bubbleSort(ArrayList<Object[]> al) 
    {
        for(int j=0; j < dataValues.size(); j++) 
        {  
            for(int i = j + 1; i < dataValues.size(); i++)
            {  
                if((dataValues.get(i)[0]).toString().compareToIgnoreCase(dataValues.get(j)[0].toString())<0)
                {  
                   Object[] words = dataValues.get(j); 
                   dataValues.set(j, dataValues.get(i));
                   dataValues.set(i, words);
                }  
            }  
        }  
        table.repaint();
        wordModel.fireTableDataChanged();
    }
    
    public void InsertionSort(ArrayList<Object[]> al)
    {
        Object[] insert;
        int position;
        String key;
        
        for (int i = 1; i < dataValues.size(); i++)
        {
             insert = dataValues.get(i);
             key = dataValues.get(i)[1].toString();
             position = i;
            
             while( (position > 0) && (dataValues.get(position -1)[1].toString().compareToIgnoreCase(key)>0))
             {
                 dataValues.set(i, dataValues.get(position -1));
                 position-- ;
                 
             }
             dataValues.set(position, insert);
                       
        }
        table.repaint();
        wordModel.fireTableDataChanged();
    }

    
    
    public  void SelectionSort( ArrayList<Object[]> al)
    {
       Object[] selection;
 
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < dataValues.size()-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < dataValues.size(); j++)
            { 
                if(dataValues.get(j)[2].toString().compareToIgnoreCase(dataValues.get(min_idx)[2].toString())<0)
                  min_idx = j;
            }
            // Swap the found minimum element with the first
            // element
            selection = dataValues.get(min_idx);
            dataValues.set(min_idx, dataValues.get(i));
            dataValues.set(i, selection);
            
        }
        table.repaint();
        wordModel.fireTableDataChanged();
   }


//
//    }
            
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnExit)
        {
            System.exit(0);
        }
        
        if (e.getSource() == btnSortQn)
        {
            bubbleSort(dataValues);
        }
        
        if (e.getSource() == btnSortTopic)
        {
            InsertionSort(dataValues);
        }
        if (e.getSource() == btnSortQuestion)
        {
            SelectionSort(dataValues);
        }
    }
    
    public void QuestionTable(SpringLayout myPanelLayout)
    { 
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] =
        { "#", "Topic", "Question" };

        // Create some data
        // Make the getter into a for loop
       dataValues = new ArrayList();
        //dataValues.add(new Object[] {"no","topic","qn"});

        
        

        // constructor of JTable model
	wordModel = new MyModel(dataValues, columnNames);
        
        // Create a new table instance
        table = new JTable(wordModel);

        // Configure some of JTable's paramters
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

        public void valueChanged(ListSelectionEvent e) {
            
                questionNumber = (table.getValueAt(table.getSelectedRow(), 0).toString());
                lblNumberQuestion.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                txtRightTopic.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                txtQuestion.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
            }
        });
        
        table.isForegroundSet();
        table.setShowHorizontalLines(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        add(table);

        // Change the text and background colours
        table.setSelectionForeground(Color.white);
        table.setSelectionBackground(Color.blue);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = table.createScrollPaneForTable(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(500, 300));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 10, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 55, SpringLayout.NORTH, this);
    }

    
    //---------------------------------------------------------------------------------------------------
    // Source: http://www.dreamincode.net/forums/topic/231112-from-basic-jtable-to-a-jtable-with-a-tablemodel/
    // class that extends the AbstractTableModel
    //---------------------------------------------------------------------------------------------------

    class MyModel extends AbstractTableModel
    {
        ArrayList<Object[]> al;

        // the headers
        String[] header;

        // constructor 
        MyModel(ArrayList<Object[]> obj, String[] header)
        {
            // save the header
            this.header = header;
            // and the data
            al = obj;
        }

        // method that needs to be overload. The row count is the size of the ArrayList

        public int getRowCount()
        {
            return al.size();
        }

        // method that needs to be overload. The column count is the size of our header
        public int getColumnCount()
        {
            return header.length;
        }

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name 
        public String getColumnName(int index)
        {
            return header[index];
        }
        
        public void addRow(int row)
        {
            
        }
        
        public void removeRow(int row) {
            dataValues.remove(row);
        }

        // a method to add a new line to the table
        void add(String word1, String word2)
        {
            // make it an array[2] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            String[] str = new String[2];
            str[0] = word1;
            str[1] = word2;
            al.add(str);
            // inform the GUI that I have change
            fireTableDataChanged();
        }  

    }


    
    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
