package teacherUtilities;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;


class Simulation extends JPanel implements ActionListener, ChangeListener, Constants
{
    static final String VERSION = "0.9.1";
    
    private static final long serialVersionUID = 4301592417000711331L;
    
    private final static int
        ANZAHL_AUFGABEN = 10,
        MIN_OPS = 3, MAX_OPS = 4,
        ADD_MIN = 30, SUB_MIN = 30,
        ADD_MAX = 150, SUB_MAX = 150;
    
    private final static boolean MULTIPLICATION_OPS_AFTER_EQUALS_SIGN = false;
    
    
    private final JButton
        start_stop_button;
    
    private final JSpinner
        no_of_sim_spinner;
    private final JSpinner no_of_operand_min_spinner;
    private final JSpinner no_of_operand_max_spinner;
    
    private final JSpinner add_sub_von_spinner;
    private final JSpinner add_sub_bis_spinner;
    private final JSpinner multiplication_von_spinner;
    private final JSpinner multiplication_bis_spinner;
    private final JSpinner dividend_von_spinner;
    private final JSpinner dividend_bis_spinner;
    private final JSpinner divisor_von_spinner;
    private final JSpinner divisor_bis_spinner;
    
    private final JCheckBox
        cb_multiple_operands_behind_equality_sign;
    private final JCheckBox cb_unknown_before_equality_sign;
    private final JCheckBox cb_mix_arithmetic_operations;
    private final JCheckBox cb_use_addition;
    private final JCheckBox cb_use_subtraction;
    private final JCheckBox cb_use_multiplication;
    private final JCheckBox cb_use_division;
    
    int no_of_sim = ANZAHL_AUFGABEN,
    
    min_no_of_ops = MIN_OPS,
        max_no_of_ops = MAX_OPS,
    
    min_add_sub_value = ADD_MIN,
        min_multiplication_value = SUB_MIN,
        max_add_sub_value = ADD_MAX,
        max_multiplication_value = ADD_MAX;
    
    
    Simulation()
    {
        this.setLayout(new BorderLayout());
        
        JPanel main_panel = new JPanel(new GridLayout(3, 1));
        main_panel.setBorder(BorderFactory.createEtchedBorder());
        this.add(main_panel, BorderLayout.CENTER);
        
        this.start_stop_button = new JButton("Matheaufgaben berechnen");
        this.start_stop_button.addActionListener(this);
        this.add(this.start_stop_button, BorderLayout.SOUTH);
        
        JPanel sub_panel_1 = new JPanel(new BorderLayout());
        sub_panel_1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Aufgabenaufbau"));
        
        JPanel sub_panel_1_1 = new JPanel(new GridLayout(3, 2));
        JPanel sub_panel_1_2 = new JPanel(new GridLayout(3, 1));
        
        JPanel sub_panel_2 = new JPanel(new GridLayout(3, 2));
        sub_panel_2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Verwendete Rechenarten"));
        
        JPanel sub_panel_3 = new JPanel(new GridLayout(4, 5));
        sub_panel_3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Zahlenbereiche"));
        
        main_panel.add(sub_panel_1);
        main_panel.add(sub_panel_2);
        main_panel.add(sub_panel_3);
        
        this.no_of_sim_spinner = new JSpinner(new SpinnerNumberModel(ANZAHL_AUFGABEN, 1, Integer.MAX_VALUE, 1));
        this.no_of_sim_spinner.addChangeListener(this);
        
        this.no_of_operand_min_spinner = new JSpinner(new SpinnerNumberModel(MIN_OPS, 3, 5, 1));
        this.no_of_operand_min_spinner.addChangeListener(this);
        
        this.no_of_operand_max_spinner = new JSpinner(new SpinnerNumberModel(MAX_OPS, 3, 5, 1));
        this.no_of_operand_max_spinner.addChangeListener(this);
        
        this.cb_multiple_operands_behind_equality_sign = new JCheckBox("Mehrere Operanden nach dem Gleichheitszeichen", MULTIPLICATION_OPS_AFTER_EQUALS_SIGN);
        this.cb_unknown_before_equality_sign = new JCheckBox("Unbekannte vor dem Gleichheitszeichen", true);
        this.cb_mix_arithmetic_operations = new JCheckBox("Gemischte Rechenoperationen", true);
        
        this.cb_use_addition = new JCheckBox("Addition", true);
        this.cb_use_addition.setHorizontalAlignment(SwingConstants.CENTER);
        this.cb_use_addition.addActionListener(this);
        this.cb_use_subtraction = new JCheckBox("Subtraction", true);
        this.cb_use_subtraction.setHorizontalAlignment(SwingConstants.CENTER);
        this.cb_use_subtraction.addActionListener(this);
        this.cb_use_multiplication = new JCheckBox("Multiplikation", false);
        this.cb_use_multiplication.setHorizontalAlignment(SwingConstants.CENTER);
        this.cb_use_multiplication.addActionListener(this);
        this.cb_use_multiplication.setEnabled(false);
        
        this.cb_use_division = new JCheckBox("Division", false);
        this.cb_use_division.setHorizontalAlignment(SwingConstants.CENTER);
        this.cb_use_division.addActionListener(this);
        this.cb_use_division.setEnabled(false);
        
        JLabel copyright_label = new JLabel("Copyright 2016 Björn Hansen");
        copyright_label.setForeground(Color.LIGHT_GRAY);
        
        this.add_sub_von_spinner = new JSpinner(new SpinnerNumberModel(ADD_MIN, 0, Integer.MAX_VALUE, 10));
        this.add_sub_von_spinner.addChangeListener(this);
        this.add_sub_von_spinner.setEnabled(this.cb_use_addition.isSelected());
        
        this.add_sub_bis_spinner = new JSpinner(new SpinnerNumberModel(ADD_MAX, 0, Integer.MAX_VALUE, 10));
        this.add_sub_bis_spinner.addChangeListener(this);
        this.add_sub_bis_spinner.setEnabled(this.cb_use_addition.isSelected());
        
        this.multiplication_von_spinner = new JSpinner(new SpinnerNumberModel(SUB_MIN, 0, Integer.MAX_VALUE, 10));
        this.multiplication_von_spinner.addChangeListener(this);
        this.multiplication_von_spinner.setEnabled(this.cb_use_multiplication.isSelected());
        
        this.multiplication_bis_spinner = new JSpinner(new SpinnerNumberModel(SUB_MAX, 0, Integer.MAX_VALUE, 10));
        this.multiplication_bis_spinner.addChangeListener(this);
        this.multiplication_bis_spinner.setEnabled(this.cb_use_multiplication.isSelected());
        
        this.dividend_von_spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 5));
        this.dividend_von_spinner.addChangeListener(this);
        this.dividend_von_spinner.setEnabled(this.cb_use_multiplication.isSelected());
        
        this.dividend_bis_spinner = new JSpinner(new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 5));
        this.dividend_bis_spinner.addChangeListener(this);
        this.dividend_bis_spinner.setEnabled(this.cb_use_multiplication.isSelected());
        
        this.divisor_von_spinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        this.divisor_von_spinner.addChangeListener(this);
        this.divisor_von_spinner.setEnabled(this.cb_use_division.isSelected());
        
        this.divisor_bis_spinner = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        this.divisor_bis_spinner.addChangeListener(this);
        this.divisor_bis_spinner.setEnabled(this.cb_use_division.isSelected());
        
        JLabel[] von_bis_labels = new JLabel[8];
        for(int i = 0; i < 8; i++)
        {
            von_bis_labels[i] = new JLabel(i % 2 == 0 ? "von" : "bis");
            von_bis_labels[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        
        sub_panel_1.add(sub_panel_1_1, BorderLayout.CENTER);
        sub_panel_1.add(sub_panel_1_2, BorderLayout.SOUTH);
        
        sub_panel_1_1.add(new JLabel("Anzahl der Aufgaben"));
        sub_panel_1_1.add(this.no_of_sim_spinner);
        
        sub_panel_1_1.add(new JLabel("Minimale Anzahl von Operanden"));
        sub_panel_1_1.add(this.no_of_operand_min_spinner);
        
        sub_panel_1_1.add(new JLabel("Maximale Anzahl von Operanden"));
        sub_panel_1_1.add(this.no_of_operand_max_spinner);
        
        sub_panel_1_2.add(this.cb_multiple_operands_behind_equality_sign);
        sub_panel_1_2.add(this.cb_unknown_before_equality_sign);
        sub_panel_1_2.add(this.cb_mix_arithmetic_operations);
        
        sub_panel_2.add(this.cb_use_addition);
        sub_panel_2.add(this.cb_use_subtraction);
        sub_panel_2.add(this.cb_use_multiplication);
        sub_panel_2.add(this.cb_use_division);
        sub_panel_2.add(new JLabel());
        sub_panel_2.add(copyright_label);
        
        sub_panel_3.add(new JLabel("Addition/Subtraction"));
        sub_panel_3.add(von_bis_labels[0]);
        sub_panel_3.add(this.add_sub_von_spinner);
        sub_panel_3.add(von_bis_labels[1]);
        sub_panel_3.add(this.add_sub_bis_spinner);
        
        sub_panel_3.add(new JLabel("Multiplikation:"));
        sub_panel_3.add(von_bis_labels[2]);
        sub_panel_3.add(this.multiplication_von_spinner);
        sub_panel_3.add(von_bis_labels[3]);
        sub_panel_3.add(this.multiplication_bis_spinner);
        
        sub_panel_3.add(new JLabel("Dividend:"));
        sub_panel_3.add(von_bis_labels[4]);
        sub_panel_3.add(this.dividend_von_spinner);
        sub_panel_3.add(von_bis_labels[5]);
        sub_panel_3.add(this.dividend_bis_spinner);
        
        sub_panel_3.add(new JLabel("Divisor:"));
        sub_panel_3.add(von_bis_labels[6]);
        sub_panel_3.add(this.divisor_von_spinner);
        sub_panel_3.add(von_bis_labels[7]);
        sub_panel_3.add(this.divisor_bis_spinner);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        
        if(o == this.start_stop_button)
        {
            String currentDateAndTime = (new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")).format(new Date(System.currentTimeMillis()));
    
            String exerciseOutputDirectoryPath = Main.LOG_DIRECTORY_PATH + currentDateAndTime + "/";
            
            File exerciseOutputDirectory = new File(exerciseOutputDirectoryPath);
            exerciseOutputDirectory.mkdir();
            
            for(int i = 0; i < this.no_of_sim; i++)
            {
                int no_of_ops = this.min_no_of_ops + Calculations.random(1 + this.max_no_of_ops - this.min_no_of_ops);
                int pos_of_equals_sign = this.cb_multiple_operands_behind_equality_sign.isSelected() ? Calculations.random(no_of_ops - 1) : no_of_ops - 2;
                int pos_of_unknown = this.cb_unknown_before_equality_sign.isSelected() ? Calculations.random(no_of_ops) : pos_of_equals_sign + 1 + Calculations.random(no_of_ops - 1 - pos_of_equals_sign);
                
                boolean before_equality_sign = true;
                int sum_before = 0, sum_behind = 0;
                StringBuilder aufgabe_unsolved = new StringBuilder();
                StringBuilder aufgabe_solved = new StringBuilder();
                String operator;
                String current_operator = "";
                
                if(this.cb_use_addition.isSelected() &&
                    !this.cb_use_subtraction.isSelected())
                {
                    operator = " + ";
                }
                else if(!this.cb_use_addition.isSelected() &&
                    this.cb_use_subtraction.isSelected())
                {
                    operator = " - ";
                }
                else if(!this.cb_mix_arithmetic_operations.isSelected())
                {
                    operator = Math.random() < 0.5 ? " + " : " - ";
                }
                else
                {
                    operator = "?";
                }
                
                for(int j = 0; j < no_of_ops; j++)
                {
                    int current_sum = before_equality_sign ? sum_before : sum_behind;
                    
                    int next_operand =
                        j == no_of_ops - 1
                            ? (current_operator.equals(" - ") ? -1 : 1) * (sum_before - sum_behind)
                            : ((!current_operator.equals(" - ")) || current_sum >= this.max_add_sub_value)
                            ? this.min_add_sub_value + Calculations.random(this.max_add_sub_value - this.min_add_sub_value)
                            : current_sum >= this.min_add_sub_value
                            ? this.min_add_sub_value + Calculations.random(current_sum - this.min_add_sub_value)
                            : Calculations.random(current_sum);
                    
                    
                    if(next_operand < 0)
                    {
                        next_operand = -next_operand;
                        current_operator =
                            current_operator.equals(" - ")
                                ? " + "
                                : " - ";
                    }
                    
                    aufgabe_solved.append(current_operator)
                                  .append(j == pos_of_unknown ? "_" : "")
                                  .append(next_operand)
                                  .append(j == pos_of_unknown ? "_" : "");
                    
                    aufgabe_unsolved.append(current_operator)
                                    .append(j == pos_of_unknown ? gap(next_operand) : next_operand);
                    
                    int sumBefore = current_operator.equals(" - ") ? -next_operand : next_operand;
                    if(before_equality_sign)
                    {
                        sum_before += sumBefore;
                    }
                    else
                    {
                        sum_behind += sumBefore;
                    }
                    
                    if(j != no_of_ops - 1)
                    {
                        if(pos_of_equals_sign != j)
                        {
                            current_operator = operator.equals("?") ? Math.random() < 0.5 ? " + " : " - " : operator;
                        }
                        else
                        {
                            current_operator = " = ";
                            before_equality_sign = false;
                        }
                        
                    }
                }
                try
                {
                    BufferedWriter bw_1 = new BufferedWriter(new FileWriter(exerciseOutputDirectoryPath + "Aufgaben.doc", true));
                    BufferedWriter bw_2 = new BufferedWriter(new FileWriter(exerciseOutputDirectoryPath + "Loesungen.doc", true));
                    bw_1.write(aufgabe_unsolved + "\n");
                    bw_2.write(aufgabe_solved + "\n");
                    bw_1.close();
                    bw_2.close();
                }
                catch(IOException ioe)
                {
                    System.out.println("caught error: " + ioe);
                }
                System.out.println(aufgabe_solved);
                System.out.println(aufgabe_unsolved);
                System.out.println();
            }
        }
        else if(o == this.cb_use_addition)
        {
            if(!this.cb_use_addition.isSelected())
            {
                this.cb_use_subtraction.setSelected(true);
                
            }
        }
        
        else if(o == this.cb_use_subtraction)
        {
            if(!this.cb_use_subtraction.isSelected())
            {
                this.cb_use_addition.setSelected(true);
                
            }
        }
        else if(o == this.cb_use_multiplication)
        {
            this.dividend_von_spinner.setEnabled(this.cb_use_multiplication.isSelected());
            this.dividend_bis_spinner.setEnabled(this.cb_use_multiplication.isSelected());
        }
        else if(o == this.cb_use_division)
        {
            this.divisor_von_spinner.setEnabled(this.cb_use_division.isSelected());
            this.divisor_bis_spinner.setEnabled(this.cb_use_division.isSelected());
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e)
    {
        Object o = e.getSource();
        
        if(o == this.no_of_sim_spinner)
        {
            this.no_of_sim = (int) Double.valueOf((this.no_of_sim_spinner).getValue()
                                                                          .toString())
                                         .doubleValue();
        }
        
        else if(o == this.no_of_operand_min_spinner)
        {
            this.min_no_of_ops = (int) Double.valueOf((this.no_of_operand_min_spinner).getValue()
                                                                                      .toString())
                                             .doubleValue();
            this.max_no_of_ops = (int) Double.valueOf((this.no_of_operand_max_spinner).getValue()
                                                                                      .toString())
                                             .doubleValue();
            
            if(this.min_no_of_ops > this.max_no_of_ops)
            {
                this.no_of_operand_max_spinner.setValue(this.min_no_of_ops);
            }
        }
        
        else if(o == this.no_of_operand_max_spinner)
        {
            this.min_no_of_ops = (int) Double.valueOf((this.no_of_operand_min_spinner).getValue()
                                                                                      .toString())
                                             .doubleValue();
            this.max_no_of_ops = (int) Double.valueOf((this.no_of_operand_max_spinner).getValue()
                                                                                      .toString())
                                             .doubleValue();
            
            if(this.min_no_of_ops > this.max_no_of_ops)
            {
                this.no_of_operand_min_spinner.setValue(this.max_no_of_ops);
            }
        }
        
        else if(o == this.add_sub_von_spinner)
        {
            this.min_add_sub_value = (int) Double.valueOf((this.add_sub_von_spinner).getValue()
                                                                                    .toString())
                                                 .doubleValue();
            this.max_add_sub_value = (int) Double.valueOf((this.add_sub_bis_spinner).getValue()
                                                                                    .toString())
                                                 .doubleValue();
            
            if(this.min_add_sub_value > this.max_add_sub_value)
            {
                this.add_sub_bis_spinner.setValue(this.min_add_sub_value);
            }
        }
        else if(o == this.add_sub_bis_spinner)
        {
            this.min_add_sub_value = (int) Double.valueOf((this.add_sub_von_spinner).getValue()
                                                                                    .toString())
                                                 .doubleValue();
            this.max_add_sub_value = (int) Double.valueOf((this.add_sub_bis_spinner).getValue()
                                                                                    .toString())
                                                 .doubleValue();
            
            if(this.min_add_sub_value > this.max_add_sub_value)
            {
                this.add_sub_von_spinner.setValue(this.max_add_sub_value);
            }
        }
        
        else if(o == this.multiplication_von_spinner)
        {
            this.min_multiplication_value = (int) Double.valueOf((this.multiplication_von_spinner).getValue()
                                                                                                  .toString())
                                                        .doubleValue();
            this.max_multiplication_value = (int) Double.valueOf((this.multiplication_bis_spinner).getValue()
                                                                                                  .toString())
                                                        .doubleValue();
            
            if(this.min_multiplication_value > this.max_multiplication_value)
            {
                this.multiplication_bis_spinner.setValue(this.min_multiplication_value);
            }
        }
        else if(o == this.multiplication_bis_spinner)
        {
            this.min_multiplication_value = (int) Double.valueOf((this.multiplication_von_spinner).getValue()
                                                                                                  .toString())
                                                        .doubleValue();
            this.max_multiplication_value = (int) Double.valueOf((this.multiplication_bis_spinner).getValue()
                                                                                                  .toString())
                                                        .doubleValue();
            
            if(this.min_multiplication_value > this.max_multiplication_value)
            {
                this.multiplication_von_spinner.setValue(this.max_multiplication_value);
            }
        }
    }
    
    static String gap(int operand)
    {
        return "___" + "_".repeat(Math.max(0, (int) (Math.floor(Math.log10(operand)))));
    }
}
