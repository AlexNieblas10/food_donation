/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Control.OrganizacionController;
import Control.DireccionController;
import Entidades.Organizacion;
import Entidades.Direccion;
import Excepciones.OrganizacionException;
import Excepciones.DireccionException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author Laptop
 */
public class RegistroOrganizacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RegistroOrganizacion.class.getName());
    private OrganizacionController organizacionController;
    private DireccionController direccionController;
    private DefaultTableModel modeloTabla;
    private int organizacionSeleccionadaId = -1;

    /**
     * Creates new form RegistroOrganizacion
     */
    public RegistroOrganizacion() {
        initComponents();
        inicializarControladores();
        configurarTabla();
        cargarDatosTabla();
        configurarEventosTabla();
    }

    private void inicializarControladores() {
        this.organizacionController = new OrganizacionController();
        this.direccionController = new DireccionController();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Organización", "Responsable", "Correo", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarDatosTabla() {
        try {
            modeloTabla.setRowCount(0);
            List<Organizacion> organizaciones = organizacionController.obtenerTodasOrganizaciones();
            for (Organizacion org : organizaciones) {
                Object[] fila = {
                    org.getIdOrganizacion(),
                    org.getNombreOrganizacion(),
                    org.getNombreResponsable(),
                    org.getCorreo(),
                    org.getTelefono()
                };
                modeloTabla.addRow(fila);
            }
        } catch (OrganizacionException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar organizaciones: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al cargar organizaciones: " + e.getMessage());
        }
    }

    private void configurarEventosTabla() {
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = jTable1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarDatosOrganizacion(filaSeleccionada);
                }
            }
        });
    }

    private void cargarDatosOrganizacion(int fila) {
        try {
            organizacionSeleccionadaId = (Integer) modeloTabla.getValueAt(fila, 0);
            Organizacion org = organizacionController.obtenerOrganizacion(organizacionSeleccionadaId);

            if (org != null) {
                jTextFieldOrganizacion.setText(org.getNombreOrganizacion());
                jTextFieldResponsable.setText(org.getNombreResponsable());
                jTextField3Correo.setText(org.getCorreo());
                jTextField4Telefono.setText(org.getTelefono());

                if (org.getIdDireccion() > 0) {
                    Direccion direccion = direccionController.obtenerDireccion(org.getIdDireccion());
                    if (direccion != null) {
                        jTextFieldCalle.setText(direccion.getCalle());
                        jTextFieldNumero.setText(direccion.getNumero());
                        jTextFieldColonia.setText(direccion.getColonia());
                        jTextFieldCiudad.setText(direccion.getCiudad());
                        jTextFieldEstado.setText(direccion.getEstado());
                        jTextFieldCodigoPos.setText(direccion.getCodigoPostal());
                    }
                }
            }
        } catch (OrganizacionException | DireccionException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al cargar datos de organización: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        jTextFieldOrganizacion.setText("");
        jTextFieldResponsable.setText("");
        jTextField3Correo.setText("");
        jTextField4Telefono.setText("");
        jTextFieldCalle.setText("");
        jTextFieldNumero.setText("");
        jTextFieldColonia.setText("");
        jTextFieldCiudad.setText("");
        jTextFieldEstado.setText("");
        jTextFieldCodigoPos.setText("");
        jTextFieldBuscar.setText("");
        organizacionSeleccionadaId = -1;
        jTable1.clearSelection();
    }

    private boolean validarCampos() {
        if (jTextFieldOrganizacion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la organización es obligatorio",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            jTextFieldOrganizacion.requestFocus();
            return false;
        }
        if (jTextFieldResponsable.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del responsable es obligatorio",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            jTextFieldResponsable.requestFocus();
            return false;
        }
        if (jTextField3Correo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo electrónico es obligatorio",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            jTextField3Correo.requestFocus();
            return false;
        }
        if (!jTextField3Correo.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            jTextField3Correo.requestFocus();
            return false;
        }
        if (jTextField4Telefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            jTextField4Telefono.requestFocus();
            return false;
        }
        return true;
    }

    public void btnNuevoActionPerformed() {
        limpiarCampos();
    }

    public void actualizarVista() {
        cargarDatosTabla();
    }

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {
        try {
            Menu menu = new Menu();
            menu.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al regresar al menú: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al regresar al menú: " + e.getMessage());
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldOrganizacion = new javax.swing.JTextField();
        jTextFieldResponsable = new javax.swing.JTextField();
        jTextField3Correo = new javax.swing.JTextField();
        jTextField4Telefono = new javax.swing.JTextField();
        jTextFieldCalle = new javax.swing.JTextField();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldColonia = new javax.swing.JTextField();
        jTextFieldCiudad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldEstado = new javax.swing.JTextField();
        jTextFieldCodigoPos = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jTextFieldBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Registro de organización");

        jLabel2.setText("Organización");

        jLabel3.setText("Responsable");

        jLabel4.setText("Correo");

        jLabel5.setText("Telefono");

        jLabel6.setText("Calle");

        jLabel7.setText("Numero");

        jLabel8.setText("Colonia");

        jLabel9.setText("Ciudad");

        jTextFieldOrganizacion.setActionCommand("TextFieldOrg");
        jTextFieldOrganizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldOrganizacionActionPerformed(evt);
            }
        });

        jTextFieldResponsable.setActionCommand("TextField");

        jTextField3Correo.setActionCommand("TextField");

        jTextField4Telefono.setActionCommand("TextField");

        jTextFieldCalle.setActionCommand("TextField");
        jTextFieldCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCalleActionPerformed(evt);
            }
        });

        jTextFieldNumero.setActionCommand("TextField");

        jTextFieldColonia.setActionCommand("TextField");

        jTextFieldCiudad.setActionCommand("TextField");

        jLabel10.setText("Estado");

        jLabel11.setText("Codigo Postal");

        jTextFieldEstado.setActionCommand("TextField");

        jTextFieldCodigoPos.setActionCommand("TextField");
        jTextFieldCodigoPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodigoPosActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setActionCommand("BtnGuardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.setActionCommand("BtnBuscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        btnEliminar.setText("Eliminar");
        btnEliminar.setActionCommand("BtnEliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jMenu1.setText("Menu");
        jMenu1.setActionCommand("BtnMenu");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Donadores");
        jMenu2.setActionCommand("BtnDonadores");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Alimentos");
        jMenu3.setActionCommand("BtnAlimentos");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Entrega");
        jMenu4.setActionCommand("BtnEntrega");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOrganizacion, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(jTextFieldResponsable)
                            .addComponent(jTextField3Correo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNumero)
                            .addComponent(jTextFieldColonia)
                            .addComponent(jTextFieldCiudad)
                            .addComponent(jTextFieldEstado)
                            .addComponent(jTextFieldCodigoPos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar)
                .addGap(118, 118, 118))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField3Correo, jTextField4Telefono, jTextFieldCalle, jTextFieldCiudad, jTextFieldCodigoPos, jTextFieldColonia, jTextFieldEstado, jTextFieldNumero, jTextFieldOrganizacion, jTextFieldResponsable});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBuscar)
                    .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3Correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCodigoPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnEliminar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextField3Correo, jTextField4Telefono, jTextFieldCalle, jTextFieldCiudad, jTextFieldCodigoPos, jTextFieldColonia, jTextFieldEstado, jTextFieldNumero, jTextFieldOrganizacion, jTextFieldResponsable});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCalleActionPerformed

    private void jTextFieldOrganizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldOrganizacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldOrganizacionActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!validarCampos()) {
            return;
        }

        try {
            Direccion direccion = new Direccion();
            direccion.setCalle(jTextFieldCalle.getText().trim());
            direccion.setNumero(jTextFieldNumero.getText().trim());
            direccion.setColonia(jTextFieldColonia.getText().trim());
            direccion.setCiudad(jTextFieldCiudad.getText().trim());
            direccion.setEstado(jTextFieldEstado.getText().trim());
            direccion.setCodigoPostal(jTextFieldCodigoPos.getText().trim());

            int idDireccion;
            if (organizacionSeleccionadaId > 0) {
                Organizacion orgExistente = organizacionController.obtenerOrganizacion(organizacionSeleccionadaId);
                if (orgExistente != null && orgExistente.getIdDireccion() > 0) {
                    direccion.setIdDireccion(orgExistente.getIdDireccion());
                    direccionController.actualizarDireccion(direccion);
                    idDireccion = orgExistente.getIdDireccion();
                } else {
                    idDireccion = direccionController.crearDireccion(direccion);
                }
            } else {
                idDireccion = direccionController.crearDireccion(direccion);
            }

            Organizacion organizacion = new Organizacion();
            organizacion.setNombreOrganizacion(jTextFieldOrganizacion.getText().trim());
            organizacion.setNombreResponsable(jTextFieldResponsable.getText().trim());
            organizacion.setCorreo(jTextField3Correo.getText().trim());
            organizacion.setTelefono(jTextField4Telefono.getText().trim());
            organizacion.setIdDireccion(idDireccion);

            if (organizacionSeleccionadaId > 0) {
                organizacion.setIdOrganizacion(organizacionSeleccionadaId);
                organizacionController.actualizarOrganizacion(organizacion);
                JOptionPane.showMessageDialog(this, "Organización actualizada exitosamente",
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                organizacionController.crearOrganizacion(organizacion);
                JOptionPane.showMessageDialog(this, "Organización registrada exitosamente",
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            limpiarCampos();
            cargarDatosTabla();

        } catch (OrganizacionException | DireccionException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar organización: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al guardar organización: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jTextFieldCodigoPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodigoPosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodigoPosActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String textoBusqueda = jTextFieldBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            cargarDatosTabla();
            return;
        }

        try {
            modeloTabla.setRowCount(0);
            List<Organizacion> organizaciones = organizacionController.obtenerTodasOrganizaciones();

            for (Organizacion org : organizaciones) {
                boolean coincide = false;

                if (org.getNombreOrganizacion().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                    org.getNombreResponsable().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                    org.getCorreo().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                    org.getTelefono().contains(textoBusqueda)) {
                    coincide = true;
                }

                if (coincide) {
                    Object[] fila = {
                        org.getIdOrganizacion(),
                        org.getNombreOrganizacion(),
                        org.getNombreResponsable(),
                        org.getCorreo(),
                        org.getTelefono()
                    };
                    modeloTabla.addRow(fila);
                }
            }

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron organizaciones que coincidan con la búsqueda",
                                            "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (OrganizacionException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar organizaciones: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error al buscar organizaciones: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (organizacionSeleccionadaId <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una organización de la tabla para eliminar",
                                        "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar esta organización?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Organizacion orgAEliminar = organizacionController.obtenerOrganizacion(organizacionSeleccionadaId);

                organizacionController.eliminarOrganizacion(organizacionSeleccionadaId);

                if (orgAEliminar != null && orgAEliminar.getIdDireccion() > 0) {
                    try {
                        direccionController.eliminarDireccion(orgAEliminar.getIdDireccion());
                    } catch (DireccionException e) {
                        logger.warning("No se pudo eliminar la dirección asociada: " + e.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(this, "Organización eliminada exitosamente",
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
                cargarDatosTabla();

            } catch (OrganizacionException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar organización: " + e.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
                logger.severe("Error al eliminar organización: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new RegistroOrganizacion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3Correo;
    private javax.swing.JTextField jTextField4Telefono;
    private javax.swing.JTextField jTextFieldBuscar;
    private javax.swing.JTextField jTextFieldCalle;
    private javax.swing.JTextField jTextFieldCiudad;
    private javax.swing.JTextField jTextFieldCodigoPos;
    private javax.swing.JTextField jTextFieldColonia;
    private javax.swing.JTextField jTextFieldEstado;
    private javax.swing.JTextField jTextFieldNumero;
    private javax.swing.JTextField jTextFieldOrganizacion;
    private javax.swing.JTextField jTextFieldResponsable;
    // End of variables declaration//GEN-END:variables
}
