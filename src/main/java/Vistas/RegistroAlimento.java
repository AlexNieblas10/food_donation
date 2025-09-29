/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import DAO.AlimentoDAO;
import Entidades.Alimento;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Laptop
 */

public class RegistroAlimento extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RegistroAlimento.class.getName());
private final AlimentoDAO alimentoDAO; // Declarar aquí
    private DefaultTableModel modeloTablaAlimentos; // Declarar aquí
    /**
     * Creates new form RegistroAlimento
     */
    public RegistroAlimento(DAO.AlimentoDAO alimentoDAO) {
        initComponents();
        this.alimentoDAO = alimentoDAO;
    }

     public RegistroAlimento() {
        initComponents();
        this.alimentoDAO = new AlimentoDAO();
        
        configurarTablaAlimentos();
        cargarTablaAlimentos(alimentoDAO.obtenerTodosLosAlimentos()); // Cargar todos al inicio
        agregarListenerTablaAlimentos();
        limpiarCampos(); // Limpiar campos al iniciar
        
        // Cargar ítems al jComboBoxCategoria (si no los pusiste ya en el diseñador)
        // Si ya están puestos en el diseñador (como "Fruta", "Verdura", etc.)
        // no necesitas este bloque. Lo dejo comentado por si acaso.
        /*
        jComboBoxCategoria.removeAllItems();
        jComboBoxCategoria.addItem("Fruta");
        jComboBoxCategoria.addItem("Verdura");
        jComboBoxCategoria.addItem("Lacteo");
        jComboBoxCategoria.addItem("Carne");
        jComboBoxCategoria.addItem("Enlatado");
        jComboBoxCategoria.addItem("Grano");
        jComboBoxCategoria.addItem("Otro");
        */
    }
    
    private void configurarTablaAlimentos() {
        modeloTablaAlimentos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        // ¡Ojo! Aquí no tenemos jTextFieldIDAlimento en tu diseño, asumiré que el ID se gestiona internamente
        // o que hay un campo ID oculto que quieres manejar. Si no hay un campo ID visible,
        // simplemente no lo muestres en la GUI, pero es útil para el backend.
        // Para este ejemplo, supondremos que se maneja internamente o que hay un ID implícito en la selección.
        modeloTablaAlimentos.setColumnIdentifiers(new String[]{"ID", "Nombre", "Categoría", "Cantidad Disponible", "Fecha Caducidad", "ID Donador"});
        jTableAlimentos.setModel(modeloTablaAlimentos);
    }
    
    // Este método ahora recibe la lista de alimentos a mostrar, puede ser todos o los buscados
    private void cargarTablaAlimentos(List<Alimento> alimentos) {
        modeloTablaAlimentos.setRowCount(0); // Limpiar filas existentes
        for (Alimento alim : alimentos) {
            modeloTablaAlimentos.addRow(new Object[]{
                alim.getIdAlimento(),
                alim.getNombreAlimento(),
                alim.getCategoria(),
                alim.getCantidadDisponible(),
                alim.getFechaCaducidad(),
                alim.getIdDonador() > 0 ? alim.getIdDonador() : "N/A"
            });
        }
    }
    
    private void agregarListenerTablaAlimentos() {
        jTableAlimentos.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && jTableAlimentos.getSelectedRow() != -1) {
                int filaSeleccionada = jTableAlimentos.getSelectedRow();
                
                // Rellenar campos con los nombres correctos de tus componentes
                // NOTA: No tienes un jTextFieldIDAlimento en tu GUI según tu código.
                // Si quieres mostrar el ID en algún lado para editarlo, deberías agregarlo a tu GUI.
                // Por ahora, asumiré que el ID lo obtenemos de la tabla internamente.
                // int idAlimentoSeleccionado = (int) modeloTablaAlimentos.getValueAt(filaSeleccionada, 0); 
                
                jTextFieldAlimento.setText(modeloTablaAlimentos.getValueAt(filaSeleccionada, 1).toString());
                jComboBoxCategoria.setSelectedItem(modeloTablaAlimentos.getValueAt(filaSeleccionada, 2).toString());
                jTextField4Cantidad.setText(modeloTablaAlimentos.getValueAt(filaSeleccionada, 3).toString());
                jDateChooserCaducidad.setDate((Date) modeloTablaAlimentos.getValueAt(filaSeleccionada, 4));
                
                Object idDonadorObj = modeloTablaAlimentos.getValueAt(filaSeleccionada, 5);
                if (idDonadorObj != null && !idDonadorObj.toString().equals("N/A")) {
                     jTextFieldIDDonador.setText(idDonadorObj.toString());
                } else {
                     jTextFieldIDDonador.setText("");
                }
            }
        });
    }

    private void limpiarCampos() {
        // No tienes un jTextFieldIDAlimento en tu GUI, así que no lo limpiamos aquí
        jTextFieldAlimento.setText("");
        jComboBoxCategoria.setSelectedIndex(0); // Seleccionar el primer ítem o uno por defecto
        jTextField4Cantidad.setText("");
        jDateChooserCaducidad.setDate(null);
        jTextFieldIDDonador.setText("");
        jTextFieldBuscar.setText(""); // Limpiar también el campo de búsqueda
        
        jTableAlimentos.clearSelection();
    }
    
    private boolean validarCampos() {
        if (jTextFieldAlimento.getText().trim().isEmpty() ||
            jComboBoxCategoria.getSelectedItem() == null || // Verificar que haya una categoría seleccionada
            jTextField4Cantidad.getText().trim().isEmpty() ||
            jDateChooserCaducidad.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios (Alimento, Categoría, Cantidad, Fecha de Caducidad).", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            double cantidad = Double.parseDouble(jTextField4Cantidad.getText().trim());
            if (cantidad <= 0) {
                 JOptionPane.showMessageDialog(this, "La cantidad disponible debe ser un número positivo.", "Error de Valor", JOptionPane.ERROR_MESSAGE);
                 return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad disponible debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!jTextFieldIDDonador.getText().trim().isEmpty()) {
             try {
                int idDonador = Integer.parseInt(jTextFieldIDDonador.getText().trim());
                if (idDonador <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID del Donador debe ser un número positivo o estar vacío.", "Error de Valor", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID del Donador debe ser un número válido o estar vacío.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAlimentos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldAlimento = new javax.swing.JTextField();
        jTextField4Cantidad = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jTextFieldBuscar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldIDDonador = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jDateChooserCaducidad = new com.toedter.calendar.JDateChooser();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        btnActualizar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        jMenuDon = new javax.swing.JMenu();
        jMenuOrg = new javax.swing.JMenu();
        jMenuEnt = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(630, 420));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Registro de alimentos");

        jLabel3.setText("Categoria");

        jLabel5.setText("Cantidad");

        jLabel6.setText("Fecha de caducidad");

        jTableAlimentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableAlimentos);

        btnEliminar.setText("Eliminar");
        btnEliminar.setActionCommand("BtnEliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel8.setText("ID Donador");

        jTextFieldAlimento.setActionCommand("TextFieldOrg");
        jTextFieldAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAlimentoActionPerformed(evt);
            }
        });

        jTextField4Cantidad.setActionCommand("TextField");

        btnBuscar.setText("Buscar");
        btnBuscar.setActionCommand("BtnBuscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTextFieldBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBuscarActionPerformed(evt);
            }
        });

        jLabel12.setText("Alimento");

        jTextFieldIDDonador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDDonadorActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setActionCommand("BtnGuardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fruta", "Verdura", "Lacteo", "Carne", "Enlatado", "Grano", "Otro" }));

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jMenu.setText("Menu");
        jMenu.setActionCommand("BtnMenu");
        jMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu);

        jMenuDon.setText("Donador");
        jMenuDon.setActionCommand("BtnDonador");
        jMenuDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDonActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuDon);

        jMenuOrg.setText("Organizacion");
        jMenuOrg.setActionCommand("BtnOrganizacion");
        jMenuOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOrgActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuOrg);

        jMenuEnt.setText("Entrega");
        jMenuEnt.setActionCommand("BtnEntrega");
        jMenuEnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEntActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuEnt);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(209, 209, 209)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnActualizar)
                .addGap(75, 75, 75)
                .addComponent(btnEliminar)
                .addGap(59, 59, 59))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(80, 80, 80))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldAlimento)
                                    .addComponent(jComboBoxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField4Cantidad)
                                    .addComponent(jDateChooserCaducidad, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                    .addComponent(jTextFieldIDDonador))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jTextField4Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jDateChooserCaducidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldIDDonador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar)
                    .addComponent(btnActualizar))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel3, jLabel5, jLabel6, jLabel8, jTextField4Cantidad, jTextFieldAlimento, jTextFieldIDDonador});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
         int filaSeleccionada = jTableAlimentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un alimento de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este alimento?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idAlimento = (int) modeloTablaAlimentos.getValueAt(filaSeleccionada, 0); // Obtener ID de la tabla
                if (alimentoDAO.eliminarAlimento(idAlimento)) {
                    JOptionPane.showMessageDialog(this, "Alimento eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarTablaAlimentos(alimentoDAO.obtenerTodosLosAlimentos()); // Recargar todos
                } else {
                    // El DAO ya muestra un mensaje si hay una restricción de clave foránea
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al obtener el ID del alimento para eliminar.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                logger.severe(() -> "Error de formato al eliminar alimento: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jTextFieldAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAlimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAlimentoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        String textoBusqueda = jTextFieldBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            cargarTablaAlimentos(alimentoDAO.obtenerTodosLosAlimentos()); // Si está vacío, cargar todos
        } else {
            List<Alimento> resultados = alimentoDAO.buscarAlimentos(textoBusqueda);
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron alimentos con ese nombre.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            }
            cargarTablaAlimentos(resultados);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jTextFieldIDDonadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDDonadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDDonadorActionPerformed

    private void jTextFieldBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
         if (!validarCampos()) {
            return;
        }
        
        // Para "Guardar", nos aseguramos que no haya una fila seleccionada
        if (jTableAlimentos.getSelectedRow() != -1) {
            JOptionPane.showMessageDialog(this, "Hay un alimento seleccionado. Use 'Limpiar' para añadir uno nuevo, o 'Actualizar' para modificar el existente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Alimento alimento = new Alimento();
        alimento.setNombreAlimento(jTextFieldAlimento.getText().trim());
        alimento.setCategoria(jComboBoxCategoria.getSelectedItem().toString());
        alimento.setCantidadDisponible(Double.parseDouble(jTextField4Cantidad.getText().trim()));
        alimento.setFechaCaducidad((Date) jDateChooserCaducidad.getDate());
        
        String idDonadorStr = jTextFieldIDDonador.getText().trim();
        if (!idDonadorStr.isEmpty()) {
            alimento.setIdDonador(Integer.parseInt(idDonadorStr));
        } else {
            alimento.setIdDonador(0); // 0 o un valor que tu DAO interprete como NULL
        }

        if (alimentoDAO.insertarAlimento(alimento)) {
            JOptionPane.showMessageDialog(this, "Alimento guardado exitosamente. ID: " + alimento.getIdAlimento(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTablaAlimentos(alimentoDAO.obtenerTodosLosAlimentos()); // Recargar todos los alimentos
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el alimento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
         if (!validarCampos()) {
            return;
        }
        
        int filaSeleccionada = jTableAlimentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un alimento de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Alimento alimento = new Alimento();
            int idAlimento = (int) modeloTablaAlimentos.getValueAt(filaSeleccionada, 0);
            alimento.setIdAlimento(idAlimento);
            
            alimento.setNombreAlimento(jTextFieldAlimento.getText().trim());
            alimento.setCategoria(jComboBoxCategoria.getSelectedItem().toString());
            alimento.setCantidadDisponible(Double.parseDouble(jTextField4Cantidad.getText().trim()));
            alimento.setFechaCaducidad((Date) jDateChooserCaducidad.getDate());
            
            String idDonadorStr = jTextFieldIDDonador.getText().trim();
            if (!idDonadorStr.isEmpty()) {
                alimento.setIdDonador(Integer.parseInt(idDonadorStr));
            } else {
                alimento.setIdDonador(0);
            }

            if (alimentoDAO.actualizarAlimento(alimento)) {
                JOptionPane.showMessageDialog(this, "Alimento actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTablaAlimentos(alimentoDAO.obtenerTodosLosAlimentos()); // Recargar todos
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el alimento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de números (ID Alimento, ID Donador o Cantidad).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            logger.severe(() -> "Error de formato al actualizar alimento: " + e.getMessage());
        }
     
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuActionPerformed
        // TODO add your handling code here:
        try {
    Vistas.Menu menu = new Vistas.Menu();
    menu.setVisible(true);
    this.dispose();
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al abrir el menú principal: " + e.getMessage(),
                                  "Error de Navegación", JOptionPane.ERROR_MESSAGE);
    logger.severe("Error al abrir Vistas.Menu: " + e.getMessage());
}
    }//GEN-LAST:event_jMenuActionPerformed

    private void jMenuDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDonActionPerformed
        // TODO add your handling code here:
        try {
    Vistas.RegistroDonador registroDonador = new Vistas.RegistroDonador();
    registroDonador.setVisible(true);
    this.dispose();
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al abrir el registro de donadores: " + e.getMessage(),
                                  "Error de Navegación", JOptionPane.ERROR_MESSAGE);
    logger.severe("Error al abrir Vistas.RegistroDonador: " + e.getMessage());
}
    }//GEN-LAST:event_jMenuDonActionPerformed

    private void jMenuEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEntActionPerformed
        // TODO add your handling code here:
        try {
    Vistas.GestionarEntrega gestionarEntrega = new Vistas.GestionarEntrega();
    gestionarEntrega.setVisible(true);
    this.dispose();
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al abrir la gestión de entregas: " + e.getMessage(),
                                  "Error de Navegación", JOptionPane.ERROR_MESSAGE);
    logger.severe("Error al abrir Vistas.GestionarEntrega: " + e.getMessage());
}
    }//GEN-LAST:event_jMenuEntActionPerformed

    private void jMenuOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOrgActionPerformed
        // TODO add your handling code here:
          try {
            RegistroOrganizacion registroOrganizacion = new RegistroOrganizacion();
            registroOrganizacion.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir el registro de organizaciones: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al abrir RegistroOrganizacion: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuOrgActionPerformed

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {
        try {
            Menu menu = new Menu();
            menu.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al regresar al menú: " + e.getMessage(),
                                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al regresar al menú: " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new RegistroAlimento(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> jComboBoxCategoria;
    private com.toedter.calendar.JDateChooser jDateChooserCaducidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuDon;
    private javax.swing.JMenu jMenuEnt;
    private javax.swing.JMenu jMenuOrg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAlimentos;
    private javax.swing.JTextField jTextField4Cantidad;
    private javax.swing.JTextField jTextFieldAlimento;
    private javax.swing.JTextField jTextFieldBuscar;
    private javax.swing.JTextField jTextFieldIDDonador;
    // End of variables declaration//GEN-END:variables
}
