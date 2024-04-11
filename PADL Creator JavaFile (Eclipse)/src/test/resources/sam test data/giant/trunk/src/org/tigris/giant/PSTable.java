package org.tigris.giant;

import javax.swing.*;

import org.tigris.giant.model.*;

import java.awt.event.*;

public class PSTable extends JPanel{

        public static JTextField nameText = new JTextField( 15 );
        public static JTextField descriptionText = new JTextField( 15 );
        public static JTextField ifConditionText = new JTextField( 15 );
        public static JTextField unlessConditionText = new JTextField( 15 );
        private JButton doneButton = new JButton( "Save Properties" );
        public static TargetNode sn;

        public PSTable(){


           // setTitle( " " );
            //setSize( 300, 200 );
            JLabel nameLabel = new JLabel( "Target Name:" );
            nameText.setMaximumSize( nameText.getPreferredSize() );

            Box hBox1 = Box.createHorizontalBox();

            hBox1.add( nameLabel );
            hBox1.add( nameText );

            JLabel descriptionLabel = new JLabel( "Target Description:" );
            descriptionText.setMaximumSize( descriptionText.getPreferredSize() );

            Box hBox2 = Box.createHorizontalBox();
            hBox2.add( descriptionLabel );
            hBox2.add( descriptionText );

            JLabel ifConditionLabel = new JLabel( "If Condition:" );
            ifConditionText.setMaximumSize( ifConditionText.getPreferredSize() );

            Box hBox3 = Box.createHorizontalBox();
            hBox3.add( ifConditionLabel );
            hBox3.add( ifConditionText );

            JLabel unlessConditionLabel = new JLabel( "Unless Condition:" );
            unlessConditionText.setMaximumSize( unlessConditionText.getPreferredSize() );

            Box hBox4 = Box.createHorizontalBox();
            hBox4.add( unlessConditionLabel );
            hBox4.add( unlessConditionText );

            Box hBox5 = Box.createHorizontalBox();
            hBox5.add( doneButton );

            ButtonListener buttonHandler = new ButtonListener();
            doneButton.addActionListener( buttonHandler );

            Box vBox = Box.createVerticalBox();
            vBox.add( Box.createVerticalStrut( 10 ) );
            vBox.add( hBox1 );
            vBox.add( Box.createVerticalStrut( 10 ) );
            vBox.add( hBox2 );
            vBox.add( Box.createVerticalStrut( 10 ) );
            vBox.add( hBox3 );
            vBox.add( Box.createVerticalStrut( 10 ) );
            vBox.add( hBox4 );
            vBox.add( Box.createVerticalStrut( 5 ) );
            vBox.add( hBox5 );

            this.add(vBox);
        }

        private class ButtonListener implements ActionListener{

            public void actionPerformed( ActionEvent e ){

                if( e.getSource() == doneButton ){

                    //Save text fields in _myObject
                    setNewProperties();
                }
            }
        }

    public static void setTargetNode( TargetNode s ){
        sn = s;
        nameText.setText( sn.getName() );
        descriptionText.setText( sn.getDescription() );
    }

    public static void clearFields(){

        nameText.setText( "" );
        descriptionText.setText( "" );
        ifConditionText.setText( "" );
        unlessConditionText.setText( "" );
    }


    private void setNewProperties(){

        sn.setName( nameText.getText().trim() );
        sn.setDescription( descriptionText.getText().trim() );
        sn.setIfCondition( ifConditionText.getText().trim() );
        sn.setUnlessCondition( unlessConditionText.getText().trim() );
    }
}

