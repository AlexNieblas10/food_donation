/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Control.DireccionController;
import Control.DonadorController;
import Entidades.Direccion;
import Entidades.Donador;
import Excepciones.DireccionException;
import Excepciones.DonadorException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Laptop
 */
public class RegistroDonador extends javax.swing.JFrame {

    private TableRowSorter<DefaultTableModel> sorter; 
    private DonadorController donadorController;
    private DireccionController direccionController;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RegistroDonador.class.getName());
    private int idDireccionSeleccionada = -1;
    private int donadorSeleccionadoId = -1;

    /**
     * Creates new form RegistroDonador
     */
    public RegistroDonador() {
        initComponents();
        this.donadorController = new DonadorController();
        this.direccionController = new DireccionController();

        cargarDonadores();
        
          // Obtiene el modelo de la tabla que ya tiene los datos y las columnas
        DefaultTableModel modelo = (DefaultTableModel) jTableBuscar.getModel();

        // Crea el sorter basandose en ese modelo
        sorter = new TableRowSorter<>(modelo);

        //  Aplica el sorter a tabla
        jTableBuscar.setRowSorter(sorter);
        
    }
    
    /**
     * Metodo para poblar la tabla con los donadores de la base de datos.
     */
    private void cargarDonadores() {
        try {
            List<Donador> donadores = donadorController.obtenerTodosDonadores();
            DefaultTableModel model = (DefaultTableModel) jTableBuscar.getModel();
            model.setRowCount(0); // limpia la tabla antes de llenarla

            // sefine las columnas de tu tabla
            model.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Ap. Paterno", "Tipo", "Telefono"});

            for (Donador donador : donadores) {
                model.addRow(new Object[]{
                    donador.getIdDonador(),
                    donador.getNombre(),
                    donador.getApellidoPaterno(),
                    donador.getTipoDonador(),
                    donador.getTelefono()
                });
            }
            jTableBuscar.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = jTableBuscar.getSelectedRow();
                    if (filaSeleccionada >= 0) {
                        cargarDatosDonadores(filaSeleccionada);
                    }
                }
            });
        } catch (DonadorException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los donadores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error en cargarDonadores: " + e.getMessage());
        }
    }

    /**
     * Metodo para limpiar todos los campos del formulario.
     */
    private void limpiarFormulario() {
        jTextFieldNombre.setText("");
        jTextFieldAppaterno.setText("");
        jTextFieldApMaterno.setText("");
        jComboBoxTipoDonador.setSelectedIndex(0);
        jTextFieldCorreo.setText("");
        jTextFieldTelefono.setText("");
        jTextFieldCalle.setText("");
        jTextFieldNumero.setText("");
        jTextFieldColonia.setText("");
        jTextFieldCiudad.setText("");
        jTextFieldEstado.setText("");
        JTextFieldCodigoPos.setText("");
    }
    
    private boolean validarCampos() {
        if (jTextFieldNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del donador es obligatorio.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jTextFieldAppaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido paterno del donador es obligatorio.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Puedes añadir más validaciones para el donador (ej. correo, teléfono)
        // if (jTextFieldCorreo.getText().trim().isEmpty() || !jTextFieldCorreo.getText().contains("@")) { ... }

        if (jTextFieldCalle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La calle de la dirección es obligatoria.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jTextFieldCiudad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La ciudad de la dirección es obligatoria.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jTextFieldEstado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El estado de la dirección es obligatorio.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Puedes añadir validación de formato para código postal, número (si es numérico), etc.
        try {
            if (!jTextFieldNumero.getText().trim().isEmpty()) {
                Integer.parseInt(jTextFieldNumero.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El número de la dirección debe ser numérico si se especifica.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // ... (añade todas las validaciones que necesites) ...

        return true; // Todos los campos son válidos
    }


    private void cargarDatosDonadores(int fila) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTableBuscar.getModel();
            donadorSeleccionadoId = (Integer) modelo.getValueAt(fila, 0);
            System.out.println(donadorSeleccionadoId);
            Donador donador = donadorController.obtenerDonador(donadorSeleccionadoId);
            idDireccionSeleccionada = donador.getIdDireccion();

            if (donador != null) {
                System.out.println(donador.getApellidoPaterno());
                jTextFieldNombre.setText(donador.getNombre());
                jTextFieldAppaterno.setText(donador.getApellidoPaterno());
                jTextFieldApMaterno.setText(donador.getApellidoMaterno());
                jTextFieldTelefono.setText(donador.getTelefono());
                jTextFieldCorreo.setText(donador.getCorreo());
                jComboBoxTipoDonador.setToolTipText(donador.getTipoDonador());


                if (donador.getIdDireccion() > 0) {
                    Direccion direccion = direccionController.obtenerDireccion(donador.getIdDireccion());
                    if (direccion != null) {
                        jTextFieldCalle.setText(direccion.getCalle());
                        jTextFieldNumero.setText(direccion.getNumero());
                        jTextFieldColonia.setText(direccion.getColonia());
                        jTextFieldCiudad.setText(direccion.getCiudad());
                        jTextFieldEstado.setText(direccion.getEstado());
                        JTextFieldCodigoPos.setText(direccion.getCodigoPostal());
                    }
                }
            }
        } catch (DonadorException | DireccionException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al cargar datos de donador: " + e.getMessage());
        }
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
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAppaterno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldApMaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxTipoDonador = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldCorreo = new javax.swing.JTextField();
        jTextFieldTelefono = new javax.swing.JTextField();
        jTextFieldCalle = new javax.swing.JTextField();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldColonia = new javax.swing.JTextField();
        jTextFieldCiudad = new javax.swing.JTextField();
        jTextFieldEstado = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        JTextFieldCodigoPos = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jTextFieldBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuscar = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenuItem();
        jMenuOrg = new javax.swing.JMenuItem();
        jMenuAli = new javax.swing.JMenuItem();
        jMenuEnt = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Registro de donador");

        jLabel2.setText("Nombre");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextFieldNombre.setActionCommand("JTextFieldNombre");
        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });

        jLabel3.setText("Apellido paterno");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextFieldAppaterno.setActionCommand("JTextField");

        jLabel4.setText("Apellido materno");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextFieldApMaterno.setActionCommand("JTextField");
        jTextFieldApMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApMaternoActionPerformed(evt);
            }
        });

        jLabel5.setText("Tipo de donador");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jComboBoxTipoDonador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SuperMercado", "Restaurante", "Particular" }));
        jComboBoxTipoDonador.setActionCommand("comboBoxTipoDonador");
        jComboBoxTipoDonador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoDonadorActionPerformed(evt);
            }
        });

        jLabel6.setText("Correo");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel7.setText("Telefono");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel8.setText("Calle");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel9.setText("Numero");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel10.setText("Colonia");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel11.setText("Ciudad");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel12.setText("Estado");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextFieldCorreo.setActionCommand("JTextField");
        jTextFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorreoActionPerformed(evt);
            }
        });

        jTextFieldTelefono.setActionCommand("JTextField");
        jTextFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefonoActionPerformed(evt);
            }
        });

        jTextFieldCalle.setActionCommand("JTextField");
        jTextFieldCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCalleActionPerformed(evt);
            }
        });

        jTextFieldNumero.setActionCommand("JTextField");

        jTextFieldColonia.setActionCommand("JTextField");

        jTextFieldCiudad.setActionCommand("JTextField");
        jTextFieldCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCiudadActionPerformed(evt);
            }
        });

        jTextFieldEstado.setActionCommand("JTextField");

        jLabel13.setText("Codigo Postal");

        JTextFieldCodigoPos.setActionCommand("JTextField");

        btnGuardar.setText("Guardar");
        btnGuardar.setActionCommand("BtnGuardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setActionCommand("BtnEliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jTextFieldBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.setActionCommand("BtnBuscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTableBuscar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableBuscar);

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

        jMenuOrg.setText("Organizaciones");
        jMenuOrg.setActionCommand("BtnOrganizaciones");
        jMenuOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOrgActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuOrg);

        jMenuAli.setText("Alimentos");
        jMenuAli.setActionCommand("BtnAlimentos");
        jMenuAli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAliActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuAli);

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(79, 79, 79)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldAppaterno)
                            .addComponent(jTextFieldApMaterno)
                            .addComponent(jComboBoxTipoDonador, 0, 204, Short.MAX_VALUE)
                            .addComponent(jTextFieldCorreo)
                            .addComponent(jTextFieldTelefono)
                            .addComponent(jTextFieldCalle)
                            .addComponent(jTextFieldNumero)
                            .addComponent(jTextFieldColonia)
                            .addComponent(jTextFieldCiudad)
                            .addComponent(jTextFieldEstado)
                            .addComponent(JTextFieldCodigoPos)
                            .addComponent(jTextFieldNombre)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActualizar)
                                .addGap(80, 80, 80)
                                .addComponent(btnEliminar)
                                .addGap(23, 23, 23)))))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JTextFieldCodigoPos, jComboBoxTipoDonador, jTextFieldApMaterno, jTextFieldAppaterno, jTextFieldCalle, jTextFieldCiudad, jTextFieldColonia, jTextFieldCorreo, jTextFieldEstado, jTextFieldNombre, jTextFieldNumero, jTextFieldTelefono});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldAppaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBoxTipoDonador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(JTextFieldCodigoPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar)
                            .addComponent(btnActualizar))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JTextFieldCodigoPos, jComboBoxTipoDonador, jTextFieldApMaterno, jTextFieldAppaterno, jTextFieldCalle, jTextFieldCiudad, jTextFieldColonia, jTextFieldCorreo, jTextFieldEstado, jTextFieldNombre, jTextFieldNumero, jTextFieldTelefono});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxTipoDonadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoDonadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoDonadorActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        try {
            // --- 1. Recolectar y Validar Datos de la Direccion ---
            String calle = jTextFieldCalle.getText();
            String numero = jTextFieldNumero.getText();
            String colonia = jTextFieldColonia.getText();
            String ciudad = jTextFieldCiudad.getText();
            String estado = jTextFieldEstado.getText();
            String codigoPostal = JTextFieldCodigoPos.getText();

            if (calle.trim().isEmpty() || ciudad.trim().isEmpty() || estado.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La calle, ciudad y estado son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- 2. Crear el objeto Direccion ---
            Direccion nuevaDireccion = new Direccion();
            nuevaDireccion.setCalle(calle);
            nuevaDireccion.setNumero(numero);
            nuevaDireccion.setColonia(colonia);
            nuevaDireccion.setCiudad(ciudad);
            nuevaDireccion.setEstado(estado);
            nuevaDireccion.setCodigoPostal(codigoPostal);

            // --- 3. Guardar la Direccion y Capturar su ID ---
            int idDireccionGuardada = direccionController.crearDireccion(nuevaDireccion);

            // valida que la direccion se haya creado correctamente
            if (idDireccionGuardada == 0) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error critico al guardar la direccion.", "Error en Base de Datos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- 4. Recolectar y Validar Datos del Donador ---
            String nombre = jTextFieldNombre.getText();
            String apPaterno = jTextFieldAppaterno.getText();
            String apMaterno = jTextFieldApMaterno.getText();
            String tipoDonador = (String) jComboBoxTipoDonador.getSelectedItem();
            String correo = jTextFieldCorreo.getText();
            String telefono = jTextFieldTelefono.getText();

            if (nombre.trim().isEmpty() || apPaterno.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y apellido paterno son obligatorios.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- 5. Crear y Guardar el Donador ---
            Donador nuevoDonador = new Donador();
            nuevoDonador.setNombre(nombre);
            nuevoDonador.setApellidoPaterno(apPaterno);
            nuevoDonador.setApellidoMaterno(apMaterno);
            nuevoDonador.setTipoDonador(tipoDonador);
            nuevoDonador.setCorreo(correo);
            nuevoDonador.setTelefono(telefono);

            //se asigna el ID de la direccion que acabamos de crear
            nuevoDonador.setIdDireccion(idDireccionGuardada);

            donadorController.crearDonador(nuevoDonador);

            // --- 6. Feedback final y Limpieza ---
            JOptionPane.showMessageDialog(this, "Donador guardado exitosamente. ", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarDonadores(); //actualiza la tabla para que se vea el nuevo registro

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Error al guardar el donador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error en btnGuardarActionPerformed: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jTextFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTelefonoActionPerformed

    private void jTextFieldCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCalleActionPerformed

    private void jTextFieldCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCiudadActionPerformed

    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreActionPerformed

    private void jTextFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCorreoActionPerformed

    private void jTextFieldApMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApMaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldApMaternoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        // 1. Obtener la fila seleccionada por el usuario
        int filaSeleccionada = jTableBuscar.getSelectedRow();

        // 2. Validar que se haya seleccionado una fila
        if (filaSeleccionada == -1) { // -1 significa que no hay ninguna fila seleccionada
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un donador de la tabla para eliminar.", "Ningun donador seleccionado", JOptionPane.WARNING_MESSAGE);
            return; // Detiene la ejecucion si no hay nada seleccionado
        }

        try {
            // 3. Obtener el ID del donador de la columna 0 de la fila seleccionada
            // El valor se obtiene como Object, asi que hay que convertirlo a Integer.
            int idDonadorEliminar = (int) jTableBuscar.getValueAt(filaSeleccionada, 0);

            // 4. Pedir confirmacion al usuario (¡Paso de seguridad crucial!)
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Esta seguro de que desea eliminar al donador con ID " + idDonadorEliminar + "?",
                    "Confirmar Eliminacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            // 5. Si el usuario hace clic en "Sí" (YES_OPTION es 0)
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Se llama al controlador para eliminar el donador
                donadorController.eliminarDonador(idDonadorEliminar);

                // Se informa al usuario del exito
                JOptionPane.showMessageDialog(this, "Donador eliminado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);

                // 6. Se actualiza la tabla para que ya no se vea el registro eliminado
                cargarDonadores();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el donador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error en btnEliminarActionPerformed: " + e.getMessage());
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jTextFieldBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:

        String textoBusqueda = jTextFieldBuscar.getText();

        if (textoBusqueda.trim().length() == 0) {
            // Si el campo de busqueda esta vacio, elimina cualquier filtro anterior
            sorter.setRowFilter(null);
        } else {
            // Aplica un filtro que no distingue mayusculas/minus
            // Buscara el texto en cualquierr columna de la tabla
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuActionPerformed
        try {
            Vistas.Menu menu = new Vistas.Menu();
            menu.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al regresar al menú: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al regresar al menú: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuActionPerformed

    private void jMenuOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOrgActionPerformed
        try {
            Vistas.RegistroOrganizacion registroOrganizacion = new Vistas.RegistroOrganizacion();
            registroOrganizacion.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir registro de organizaciones: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al abrir RegistroOrganizacion: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuOrgActionPerformed

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

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        if (!validarCampos()) {
            return;
        }

        if (donadorSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un donador de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println(idDireccionSeleccionada);
        if (this.idDireccionSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la ID de la dirección del donador seleccionado. Vuelva a seleccionar el donador.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            // Crear el objeto Direccion
            Direccion direccionActualizada = new Direccion();
            direccionActualizada.setIdDireccion(this.idDireccionSeleccionada);
            direccionActualizada.setCalle(jTextFieldCalle.getText().trim());
            direccionActualizada.setNumero(jTextFieldNumero.getText().trim());
            direccionActualizada.setColonia(jTextFieldColonia.getText().trim());
            direccionActualizada.setCiudad(jTextFieldCiudad.getText().trim());
            direccionActualizada.setEstado(jTextFieldEstado.getText().trim());
            direccionActualizada.setCodigoPostal(JTextFieldCodigoPos.getText().trim());
            
            // Crear el objeto Donador
            Donador donadorActualizado = new Donador();
            donadorActualizado.setIdDonador(donadorSeleccionadoId);
            donadorActualizado.setNombre(jTextFieldNombre.getText().trim());
            donadorActualizado.setApellidoPaterno(jTextFieldAppaterno.getText().trim());
            donadorActualizado.setApellidoMaterno(jTextFieldApMaterno.getText().trim());
            donadorActualizado.setTipoDonador((String) jComboBoxTipoDonador.getSelectedItem());
            donadorActualizado.setCorreo(jTextFieldCorreo.getText().trim());
            donadorActualizado.setTelefono(jTextFieldTelefono.getText().trim());
            donadorActualizado.setIdDireccion(this.idDireccionSeleccionada);


            direccionController.actualizarDireccion(direccionActualizada);
            donadorController.actualizarDonador(donadorActualizado);

          
            JOptionPane.showMessageDialog(this, "Donador y dirección actualizados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario(); // Limpia los campos y resetea idDireccionSeleccionada
            cargarDonadores(); // Recarga la tabla para mostrar los cambios
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato en números (ej. número de dirección, código postal). Asegúrese de que los campos numéricos sean válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
           
        } catch (DonadorException e) { // Captura excepciones específicas del DAO/Controller
  
            JOptionPane.showMessageDialog(this, "Error de base de datos al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           
        } catch (Exception e) { // Captura cualquier otra excepción inesperada
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jMenuAliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAliActionPerformed
        // TODO add your handling code here:
        try {
    Vistas.RegistroAlimento registroAlimento = new Vistas.RegistroAlimento(); 
    registroAlimento.setVisible(true);
    this.dispose();
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al abrir el registro de alimentos: " + e.getMessage(),
                                  "Error de Navegación", JOptionPane.ERROR_MESSAGE);
    logger.severe("Error al abrir Vistas.RegistroAlimento: " + e.getMessage());
}
    }//GEN-LAST:event_jMenuAliActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new RegistroDonador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldCodigoPos;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> jComboBoxTipoDonador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenu;
    private javax.swing.JMenuItem jMenuAli;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuEnt;
    private javax.swing.JMenuItem jMenuOrg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBuscar;
    private javax.swing.JTextField jTextFieldApMaterno;
    private javax.swing.JTextField jTextFieldAppaterno;
    private javax.swing.JTextField jTextFieldBuscar;
    private javax.swing.JTextField jTextFieldCalle;
    private javax.swing.JTextField jTextFieldCiudad;
    private javax.swing.JTextField jTextFieldColonia;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldEstado;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldNumero;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
