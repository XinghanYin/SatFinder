import javax.swing.*;
import java.awt.Font;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.*; // List, ArrayList

public class SatGUI_6 extends JFrame
{
  private JTextArea textArea;
  private JTextField min_inc = new JTextField();
  private JTextField max_inc = new JTextField();
  private JTextField min_mm = new JTextField();
  private JTextField max_mm = new JTextField();
  private JTextField min_ecc = new JTextField();
  private JTextField max_ecc = new JTextField();
  private JTextField min_year = new JTextField();
  private JTextField max_year = new JTextField();
  private JTextField match_count = new JTextField();

  List<Satellite> sat_list;

  public SatGUI_6()
  {
    super("Sat Finder");
    setSize(900, 600);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(new JLabel(" ")); // vertical spacer
    mainPanel.add(createEntryPanel());
    mainPanel.add(new JLabel(" ")); // vertical spacer
    mainPanel.add(createHeadings());
    mainPanel.add(createTextAreaPanel());
    mainPanel.add(createButtonPanel());

    getContentPane().add(mainPanel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  private JPanel createEntryPanel()
  {
    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

    JPanel incPanel = new JPanel();
    incPanel.setLayout(new BoxLayout(incPanel, BoxLayout.X_AXIS));
    incPanel.add(new JLabel("  Min Inclination:"));
    incPanel.add(min_inc);
    incPanel.add(new JLabel("  Max Inclination:"));
    incPanel.add(max_inc);
    entryPanel.add(incPanel);

    JPanel mmPanel = new JPanel();
    mmPanel.setLayout(new BoxLayout(mmPanel, BoxLayout.X_AXIS));
    mmPanel.add(new JLabel("  Min Mean Motion:"));
    mmPanel.add(min_mm);
    mmPanel.add(new JLabel("  Max Mean Motion:"));
    mmPanel.add(max_mm);
    entryPanel.add(mmPanel);

    JPanel eccPanel = new JPanel();
    eccPanel.setLayout(new BoxLayout(eccPanel, BoxLayout.X_AXIS));
    eccPanel.add(new JLabel("  Min Eccentricity:"));
    eccPanel.add(min_ecc);
    eccPanel.add(new JLabel("  Max Eccentricity:"));
    eccPanel.add(max_ecc);
    entryPanel.add(eccPanel);

    JPanel yearPanel = new JPanel();
    yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.X_AXIS));
    yearPanel.add(new JLabel("  Min Launch Year:"));
    yearPanel.add(min_year);
    yearPanel.add(new JLabel("  Max Launch Year:"));
    yearPanel.add(max_year);
    entryPanel.add(yearPanel);

    return entryPanel;
  }

  private JPanel createHeadings()
  {
    JPanel headingsPanel = new JPanel();
    headingsPanel.setLayout(new BoxLayout(headingsPanel, BoxLayout.X_AXIS));
    JLabel headings = new JLabel("Inclination     Mean Motion       Eccentricity       Year      Catalog       Name                                     ");
    headings.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
    headingsPanel.add(headings);
    return headingsPanel;
  }

  private JPanel createTextAreaPanel()
  {
    JPanel textAreaPanel = new JPanel();
    textArea = new JTextArea(120, 50);
    textArea.setEditable(false);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(textArea);
    textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
    textAreaPanel.add(scrollPane);
    return textAreaPanel;
  }

  private JPanel createButtonPanel()
  {
    JPanel buttonPanel = new JPanel();

    JButton findButton = new JButton("Find");
    findButton.addActionListener(e -> find_sats());

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(e -> {
      textArea.setText(null);
      match_count.setText(null);
    });

    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> resetDefaults());

    buttonPanel.setLayout(new GridLayout(1, 5, 10, 5));
    buttonPanel.add(findButton);
    buttonPanel.add(clearButton);
    buttonPanel.add(resetButton);
    buttonPanel.add(new JLabel("Matches:"));
    buttonPanel.add(match_count);
    return buttonPanel;
  }

  private void resetDefaults() {
    min_inc.setText("0");
    max_inc.setText("180");
    min_mm.setText("0");
    max_mm.setText("18");
    min_ecc.setText("0");
    max_ecc.setText("1");
    min_year.setText("1957");
    max_year.setText("2056");
  }

  private void find_sats()
  {
    textArea.setText(null);
    int matches=0;
    try
    {
      sat_list = SatListURLReader.find_sats();

      for(Satellite s : sat_list)
        if (s.inclination() >= Double.valueOf(min_inc.getText())
                && s.inclination() <= Double.valueOf(max_inc.getText())
                && s.mean_motion() >= Double.parseDouble(min_mm.getText())
                && s.mean_motion() <= Double.parseDouble(max_mm.getText())
                && s.eccentricity() >= Double.valueOf(min_ecc.getText())
                && s.eccentricity() <= Double.valueOf(max_ecc.getText())
                && s.launch_year_yyyy() >= Double.valueOf(min_year.getText())
                && s.launch_year_yyyy() <= Double.valueOf(max_year.getText()) )
        {
          textArea.append(String.format("%8.2f      %14.8f      %9.7f        %6d   %8s        %s\n",
                  s.inclination(), s.mean_motion(), s.eccentricity(),
                  s.launch_year_yyyy(), s.catalog_number(), s.name() ));
          matches++;
        }
      match_count.setText(String.valueOf(matches));
    }
    catch(Exception e) {System.out.println("Error finding Satellites");}
  }


  public static void main(String[] argv)
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        SatGUI_6 example = new SatGUI_6();
      }
    });
  }
}