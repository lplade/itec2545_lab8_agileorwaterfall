package name.lplade;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lade on 10/25/16.
 */
public class AgileOrWaterfallGUI extends JFrame {
    //Initialize the variables that this form sets and passes
    private String projectName;
    private Integer programmers = 0;
    private Boolean deadlines, analysisExperience, qualityControl, earlyIntegration, workingModels;

    //Getter methods for these

    public String getProjectName() {
        return projectName;
    }

    public Integer getProgrammers() {
        return programmers;
    }

    public Boolean getDeadlines() {
        return deadlines;
    }

    public Boolean getAnalysisExperience() {
        return analysisExperience;
    }

    public Boolean getQualityControl() {
        return qualityControl;
    }

    public Boolean getEarlyIntegration() {
        return earlyIntegration;
    }

    public Boolean getWorkingModels() {
        return workingModels;
    }

    //GUI COMPONENTS

    private JPanel rootPanel;
    private JRadioButton deadlinesYesRadioButton;
    private JRadioButton deadlinesNoRadioButton;
    private JRadioButton analysisExperienceYesRadioButton;
    private JRadioButton qualityControlYesRadioButton;
    private JRadioButton earlyIntegrationYesRadioButton;
    private JRadioButton workingModelsYesRadioButton;
    private JRadioButton analysisExperienceNoRadioButton;
    private JRadioButton qualityControlNoRadioButton;
    private JRadioButton earlyIntegrationNoRadioButton;
    private JRadioButton workingModelsNoRadioButton;
    private JTextField projectNameTextField;
    private JButton recommendButton;
    private JSpinner programmersSpinner;
    private JLabel recommendationLabel;

    //The actual constructor

    AgileOrWaterfallGUI() {
        super("Agile or Waterfall?");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Project name jtextfield listener


        //Number spinner listener
        programmersSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                programmers = (Integer) programmersSpinner.getValue();
            }
        });
        //TODO do stuff to keep spinner from being set to illegal value

        //TODO there has GOT to be a way to build these iteratively

        //group the radio buttons
        ButtonGroup deadlinesGroup = new ButtonGroup();
        deadlinesGroup.add(deadlinesYesRadioButton);
        deadlinesGroup.add(deadlinesNoRadioButton);

        ButtonGroup analysisExperienceGroup = new ButtonGroup();
        analysisExperienceGroup.add(analysisExperienceYesRadioButton);
        analysisExperienceGroup.add(analysisExperienceNoRadioButton);

        ButtonGroup qualityControlGroup = new ButtonGroup();
        qualityControlGroup.add(qualityControlYesRadioButton);
        qualityControlGroup.add(qualityControlNoRadioButton);

        ButtonGroup earlyIntegrationGroup = new ButtonGroup();
        earlyIntegrationGroup.add(earlyIntegrationYesRadioButton);
        earlyIntegrationGroup.add(earlyIntegrationNoRadioButton);

        ButtonGroup workingModelGroup = new ButtonGroup();
        workingModelGroup.add(workingModelsYesRadioButton);
        workingModelGroup.add(workingModelsNoRadioButton);

        //Register listeners for the radio buttons

        ActionListener deadlinesListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deadlines = ynToBool(e.getActionCommand());
            }
        };
        deadlinesYesRadioButton.addActionListener(deadlinesListener);
        deadlinesNoRadioButton.addActionListener(deadlinesListener);

        ActionListener analysisExperienceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    analysisExperience = ynToBool(e.getActionCommand());

            }
        };
        analysisExperienceYesRadioButton.addActionListener(analysisExperienceListener);
        analysisExperienceNoRadioButton.addActionListener(analysisExperienceListener);

        ActionListener qcListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    qualityControl = ynToBool(e.getActionCommand());

            }
        };
        qualityControlYesRadioButton.addActionListener(qcListener);
        qualityControlNoRadioButton.addActionListener(qcListener);

        ActionListener earlyIntegrationListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    earlyIntegration = ynToBool(e.getActionCommand());

            }
        };
        earlyIntegrationYesRadioButton.addActionListener(earlyIntegrationListener);
        earlyIntegrationNoRadioButton.addActionListener(earlyIntegrationListener);

        ActionListener workingModelsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    workingModels = ynToBool(e.getActionCommand());
                }

        };
        workingModelsYesRadioButton.addActionListener(workingModelsListener);
        workingModelsNoRadioButton.addActionListener(workingModelsListener);

        //Recommend button listener
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectName = projectNameTextField.getText();
                Boolean aOrW;
                String recommendation;
                if (programmers < 1 || deadlines == null|| analysisExperience == null || qualityControl == null || earlyIntegration == null || workingModels == null){
                    recommendation = "PLEASE SET ALL OPTIONS";
                    recommendationLabel.setText(recommendation);
                    //TODO graceful UI error display
                } else {
                    aOrW = agileOrWaterfall(programmers, deadlines, analysisExperience, qualityControl, earlyIntegration, workingModels);
                    if (aOrW){
                        recommendation = "Agile methodology might be a good fit for " + projectName;
                    } else {
                        recommendation = "You should probably stick to waterfall for " + projectName;
                    }
                    recommendationLabel.setText(recommendation);
                }
            }
        });


    }

    // HELPER METHODS

    //Radio button actionListener returns string Yes or No.
    //Convert this into a Boolean
    private Boolean ynToBool(String yesOrNo){
        switch (yesOrNo) {
            case "Yes":
                return Boolean.TRUE;
            case "No":
                return Boolean.FALSE;
            default:
                //we should never get here
                throw new AssertionError(yesOrNo);
        }
    }

    //Core method for agileOrWaterfall heuristic
    //true = agile, false = waterfall
    public static Boolean agileOrWaterfall(
            int prog, Boolean dead, Boolean exp, Boolean qc, Boolean early, Boolean model
    ){
        //Test each question in the way that favors Agile. Increment score if true.
        //TODO weighted scores based on relative influence of factors
        int score = 0;
        Boolean agileTrue;
        if (prog < 10) {
            score++;
        }
        if (!dead) {
            score++;
        }
        if (exp) {
            score++;
        }
        if (!qc) {
            score++;
        }
        if (early) {
            score++;
        }
        if (model) {
            score++;
        }
        if (score >= 3){
            agileTrue = true;
        } else {
            agileTrue = false;
        }
        return agileTrue;
    }


}
