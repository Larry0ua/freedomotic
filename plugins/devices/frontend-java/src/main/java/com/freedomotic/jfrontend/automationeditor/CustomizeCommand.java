/**
 *
 * Copyright (c) 2009-2016 Freedomotic team http://freedomotic.com
 *
 * This file is part of Freedomotic
 *
 * This Program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Freedomotic; see the file COPYING. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.freedomotic.jfrontend.automationeditor;

import com.freedomotic.api.Client;
import com.freedomotic.api.Plugin;
import com.freedomotic.plugins.ClientStorage;
import com.freedomotic.reactions.Command;
import com.freedomotic.i18n.I18n;
import com.freedomotic.reactions.CommandRepository;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Enrico Nicoletti
 */
public class CustomizeCommand
        extends javax.swing.JFrame {

    private final static Logger LOG = LoggerFactory.getLogger(CustomizeCommand.class.getName());
    private Command original;
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table;
    private final transient I18n I18n;
    @Inject
    private transient ClientStorage clients;
    private transient final CommandRepository commandRepository;

    /**
     * Creates new form CustomizeEvent
     *
     * @param i18n
     * @param original
     * @param commandRepository
     */
    public CustomizeCommand(I18n i18n, Command original, CommandRepository commandRepository) {
        this.I18n = i18n;
        this.commandRepository = commandRepository;
        initComponents();
        this.original = original;
        txtName.setText(original.getName());
        txtDescription.setText(original.getDescription());

        model.addColumn(I18n.msg("property"));
        model.addColumn(I18n.msg("value"));
        table = new JTable(model);
        pnlParam.add(table);

        int row = 0;

        for (Entry e : original.getProperties().entrySet()) {
            List<String> list = new ArrayList<>();
            list.add(e.getKey().toString());
            list.add(e.getValue().toString());
            model.insertRow(row,
                    list.toArray());
            row++;
        }

        this.toFront();
    }

    private void enqueueReceivers() {
        DefaultComboBoxModel<String> receiversModel = new DefaultComboBoxModel<>();

        for (Client c : clients.getClients()) {
            receiversModel.addElement(c.getName());
        }

        cmbReceiver.setModel(receiversModel);
    }

    private Command fillWithFormData() {
        try {
            table.getCellEditor().stopCellEditing();
        } catch (Exception e) {
        }

        Command c = new Command();
        c.setName(txtName.getText());
        c.setDescription(txtDescription.getText());

        if (cmbReceiver.isEnabled()) {
            Plugin plugin = (Plugin) clients.get((String) cmbReceiver.getSelectedItem());
            c.setReceiver(plugin.getReadQueue());
        } else {
            c.setReceiver(original.getReceiver());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Receiver for \"{}\" is \"{}\"", c.getName(), c.getReceiver());
        }
        for (int r = 0; r < model.getRowCount(); r++) {
            c.setProperty(model.getValueAt(r, 0).toString(),
                    model.getValueAt(r, 1).toString());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(c.getProperties().toString());
        }
        return c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(  )
    {
        btnSave = new javax.swing.JButton(  );
        jLabel1 = new javax.swing.JLabel(  );
        txtName = new javax.swing.JTextField(  );
        jLabel2 = new javax.swing.JLabel(  );
        txtDescription = new javax.swing.JTextField(  );
        jLabel3 = new javax.swing.JLabel(  );
        btnEdit = new javax.swing.JButton(  );
        cmbReceiver = new javax.swing.JComboBox<>(  );
        txtReceiver = new javax.swing.JLabel(  );
        btnChangeReceiver = new javax.swing.JButton(  );
        btnDelete = new javax.swing.JButton(  );
        jScrollPane1 = new javax.swing.JScrollPane(  );
        pnlParam = new javax.swing.JPanel(  );
        txtAddRow = new javax.swing.JButton(  );

        setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE );

        btnSave.setText( I18n.msg( "save_as_new" ) );
        btnSave.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnSaveActionPerformed( evt );
                }
            } );

        jLabel1.setText( I18n.msg( "name" ) + ":" );

        jLabel2.setText( I18n.msg( "description" ) + ":" );

        jLabel3.setText( I18n.msg( "parameters" ) + ":" );

        btnEdit.setText( I18n.msg( "save_changes" ) );
        btnEdit.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnEditActionPerformed( evt );
                }
            } );

        cmbReceiver.setEnabled( false );

        txtReceiver.setText( I18n.msg( "performed_by" ) );
        txtReceiver.setEnabled( false );

        btnChangeReceiver.setText( I18n.msg( "change" ) );
        btnChangeReceiver.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnChangeReceiverActionPerformed( evt );
                }
            } );

        btnDelete.setText( I18n.msg( "delete_X",
                                     new Object[] { I18n.msg( "command" ) } ) );
        btnDelete.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnDeleteActionPerformed( evt );
                }
            } );

        jScrollPane1.setVerticalScrollBarPolicy( javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        pnlParam.setLayout( new java.awt.BorderLayout(  ) );
        jScrollPane1.setViewportView( pnlParam );

        txtAddRow.setText( I18n.msg( "add_X",
                                     new Object[] { I18n.msg( "parameter" ) } ) );
        txtAddRow.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    txtAddRowActionPerformed( evt );
                }
            } );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane(  ) );
        getContentPane(  ).setLayout( layout );
        layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                         .addGroup( layout.createSequentialGroup(  ).addContainerGap(  )
                                                          .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                           .addComponent( jScrollPane1,
                                                                                          javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                          590, Short.MAX_VALUE )
                                                                           .addGroup( layout.createSequentialGroup(  )
                                                                                            .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                             .addComponent( jLabel1,
                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                            103,
                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                             .addComponent( jLabel2 )
                                                                                                             .addComponent( txtReceiver ) )
                                                                                            .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                            .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                             .addComponent( txtDescription,
                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                            483,
                                                                                                                            Short.MAX_VALUE )
                                                                                                             .addGroup( layout.createSequentialGroup(  )
                                                                                                                              .addComponent( cmbReceiver,
                                                                                                                                             0,
                                                                                                                                             385,
                                                                                                                                             Short.MAX_VALUE )
                                                                                                                              .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                              .addComponent( btnChangeReceiver ) )
                                                                                                             .addComponent( txtName,
                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                            483,
                                                                                                                            Short.MAX_VALUE ) ) )
                                                                           .addGroup( layout.createSequentialGroup(  )
                                                                                            .addComponent( jLabel3 )
                                                                                            .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                            .addComponent( txtAddRow ) )
                                                                           .addGroup( layout.createSequentialGroup(  )
                                                                                            .addComponent( btnEdit )
                                                                                            .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED )
                                                                                            .addComponent( btnSave,
                                                                                                           javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                           165,
                                                                                                           javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                            .addGap( 10, 10, 10 )
                                                                                            .addComponent( btnDelete ) ) )
                                                          .addContainerGap(  ) ) );
        layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                       .addGroup( layout.createSequentialGroup(  ).addContainerGap(  )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( jLabel1 )
                                                                         .addComponent( txtName,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( jLabel2,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE )
                                                                         .addComponent( txtDescription,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        41,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( txtReceiver )
                                                                         .addComponent( cmbReceiver,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                         .addComponent( btnChangeReceiver ) )
                                                        .addGap( 18, 18, 18 )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( jLabel3 ).addComponent( txtAddRow ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addComponent( jScrollPane1,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE, 226,
                                                                       javax.swing.GroupLayout.PREFERRED_SIZE )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( btnDelete ).addComponent( btnEdit )
                                                                         .addComponent( btnSave ) ).addContainerGap(  ) ) );

        jLabel3.getAccessibleContext(  ).setAccessibleName( "" );

        pack(  );
    } // </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnSaveActionPerformed

        Command c = fillWithFormData();
        c.setEditable(true); //sets it needs to be saved on disk

        int preSize = commandRepository.findAll().size();
        commandRepository.create(c);

        int postSize = commandRepository.findAll().size();

        if (preSize < postSize) {
            LOG.info("Command addedd correctly [{} commands]", postSize);
        } else {
            LOG.error("Error while adding a command");
        }
        this.dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnChangeReceiverActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnChangeReceiverActionPerformed
        enqueueReceivers();
        txtReceiver.setEnabled(true);
        cmbReceiver.setEnabled(true);
    }//GEN-LAST:event_btnChangeReceiverActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnEditActionPerformed

        Command newCommand = fillWithFormData();
        newCommand.setEditable(true);

        int preSize = commandRepository.findAll().size();
        commandRepository.delete(original);
        commandRepository.create(newCommand);

        int postSize = commandRepository.findAll().size();

        if (preSize == postSize) {
            LOG.info("Command edited correctly [{} commands]", postSize);
        } else {
            LOG.error("Error while edit a command");
        }
        this.dispose();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnDeleteActionPerformed
        LOG.info("Trying to remove a command from the list");
        commandRepository.delete(original);
        this.dispose();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtAddRowActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_txtAddRowActionPerformed
        model.addRow(new Object[]{"", "", "", ""});
    }//GEN-LAST:event_txtAddRowActionPerformed
      // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JButton btnChangeReceiver;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbReceiver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlParam;
    private javax.swing.JButton txtAddRow;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtReceiver;

    // End of variables declaration//GEN-END:variables
}
