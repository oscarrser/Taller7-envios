
package interfaz;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import logica.Envio;
import logica.EnvioEstandar;
import logica.EnvioExpress;
import logica.EnvioInternacional;

public class JFrameEnvios extends javax.swing.JFrame {

    private ArrayList<Envio> envios = new ArrayList<>();

    private javax.swing.JComboBox<String> cmbModalidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtHoraLimite;
    private javax.swing.JTextField txtPaisDestino;

    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblHoraLimite;
    private javax.swing.JLabel lblPaisDestino;

    private javax.swing.JButton btnRegistrar;
    private javax.swing.JTable tablaEnvios;

    public JFrameEnvios() {

        setTitle("Registro de Envíos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        crearInterfaz();
    }

    private void crearInterfaz() {

        cmbModalidad = new javax.swing.JComboBox<>(
            new String[]{"Estándar", "Express", "Internacional"}
        );

        txtCodigo = new javax.swing.JTextField();
        txtDestinatario = new javax.swing.JTextField();
        txtPeso = new javax.swing.JTextField();

        txtDireccion = new javax.swing.JTextField();
        txtHoraLimite = new javax.swing.JTextField();
        txtPaisDestino = new javax.swing.JTextField();

        lblDireccion = new javax.swing.JLabel("Dirección:");
        lblHoraLimite = new javax.swing.JLabel("Hora límite:");
        lblPaisDestino = new javax.swing.JLabel("País destino:");

        btnRegistrar = new javax.swing.JButton("Registrar");

        tablaEnvios = new javax.swing.JTable();

        tablaEnvios.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Código",
                "Destinatario",
                "Peso",
                "Tipo",
                "Costo"
            }
        ));

        javax.swing.JPanel panel = new javax.swing.JPanel();

        panel.setLayout(new java.awt.GridLayout(0, 2, 10, 10));

        panel.add(new javax.swing.JLabel("Modalidad:"));
        panel.add(cmbModalidad);

        panel.add(new javax.swing.JLabel("Código:"));
        panel.add(txtCodigo);

        panel.add(new javax.swing.JLabel("Destinatario:"));
        panel.add(txtDestinatario);

        panel.add(new javax.swing.JLabel("Peso:"));
        panel.add(txtPeso);

        panel.add(lblDireccion);
        panel.add(txtDireccion);

        panel.add(lblHoraLimite);
        panel.add(txtHoraLimite);

        panel.add(lblPaisDestino);
        panel.add(txtPaisDestino);

        panel.add(new javax.swing.JLabel(""));
        panel.add(btnRegistrar);

        setLayout(new java.awt.BorderLayout(10, 10));

        add(panel, java.awt.BorderLayout.NORTH);

        add(new javax.swing.JScrollPane(tablaEnvios),
            java.awt.BorderLayout.CENTER);

        cmbModalidad.addActionListener(e -> actualizarCampos());

        btnRegistrar.addActionListener(e -> registrarEnvio());

        actualizarCampos();
    }

    private void actualizarCampos() {

        String modalidad =
            cmbModalidad.getSelectedItem().toString();

        lblDireccion.setVisible(false);
        txtDireccion.setVisible(false);

        lblHoraLimite.setVisible(false);
        txtHoraLimite.setVisible(false);

        lblPaisDestino.setVisible(false);
        txtPaisDestino.setVisible(false);

        if (modalidad.equals("Estándar")) {

            lblDireccion.setVisible(true);
            txtDireccion.setVisible(true);

        } else if (modalidad.equals("Express")) {

            lblHoraLimite.setVisible(true);
            txtHoraLimite.setVisible(true);

        } else if (modalidad.equals("Internacional")) {

            lblPaisDestino.setVisible(true);
            txtPaisDestino.setVisible(true);
        }

        revalidate();
        repaint();
    }

    private void registrarEnvio() {

        try {

            String codigo = txtCodigo.getText();
            String destinatario = txtDestinatario.getText();
            double peso = Double.parseDouble(txtPeso.getText());

            String modalidad =
                cmbModalidad.getSelectedItem().toString();

            Envio envio = null;

            if (modalidad.equals("Estándar")) {

                envio = new EnvioEstandar(
                    codigo,
                    destinatario,
                    peso,
                    txtDireccion.getText()
                );

            } else if (modalidad.equals("Express")) {

                envio = new EnvioExpress(
                    codigo,
                    destinatario,
                    peso,
                    txtHoraLimite.getText()
                );

            } else if (modalidad.equals("Internacional")) {

                envio = new EnvioInternacional(
                    codigo,
                    destinatario,
                    peso,
                    txtPaisDestino.getText()
                );
            }

            envios.add(envio);

            actualizarTabla();

            JOptionPane.showMessageDialog(
                this,
                "Envío registrado correctamente."
            );

            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "El peso debe ser un número válido."
            );
        }
    }

    private void actualizarTabla() {

        DefaultTableModel modelo =
            (DefaultTableModel) tablaEnvios.getModel();

        modelo.setRowCount(0);

        for (Envio envio : envios) {

            modelo.addRow(new Object[]{
                envio.getCodigo(),
                envio.getDestinatario(),
                envio.getPeso(),
                envio.getTipo(),
                envio.calcularCosto()
            });
        }
    }

    private void limpiarCampos() {

        txtCodigo.setText("");
        txtDestinatario.setText("");
        txtPeso.setText("");

        txtDireccion.setText("");
        txtHoraLimite.setText("");
        txtPaisDestino.setText("");
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new JFrameEnvios().setVisible(true);
        });
    }
}
