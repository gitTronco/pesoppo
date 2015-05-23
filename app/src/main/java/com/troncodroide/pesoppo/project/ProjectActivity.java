package com.troncodroide.pesoppo.project;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions.DuplicatedIdException;
import com.troncodroide.pesoppo.exceptions.SqlExceptions.IdNotFoundException;
import com.troncodroide.pesoppo.exceptions.SqlExceptions.UniqueKeyException;
import com.troncodroide.pesoppo.fragments.DatePickerFragment;
import com.troncodroide.pesoppo.fragments.NotificationsFragment;
import com.troncodroide.pesoppo.fragments.MultiNotificationsFragment.Message;
import com.troncodroide.pesoppo.util.ValidateUtil;

import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ProjectActivity extends FragmentActivity {
    public static final int RESULT_PROJECT_OK = 12;


    private EditText nombreEdit;
    private Button datePikerButton;
    private Button saveButton;
    private EditText descripcionEdit;
    private Proyecto proyecto;
    private SqlLiteManager manager;
    private DatePickerFragment dateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_proyecto);
        manager = new SqlLiteManager(getApplicationContext());

        nombreEdit = (EditText) findViewById(R.id.editTextNombre);
        descripcionEdit = (EditText) findViewById(R.id.editTextDescripcion);
        datePikerButton = (Button) findViewById(R.id.butonFechaInicio);
        saveButton = (Button) findViewById(R.id.button_crear_modificar_proyecto);

        dateFragment = new DatePickerFragment();
        dateFragment.setOnDateSetListener(new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String dateText = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                if (ValidateUtil.validateDate(dateText)) {
                    datePikerButton.setText(dateText);
                    proyecto.setFechaInicio(dateText);
                } else {
                    Message msg = new Message(
                            "Fecha no valida",
                            NotificationsFragment.Notification_Warning, false,
                            4);
                    sendNotification(msg);
                }
            }
        });
        Intent i = getIntent();
        if (i.hasExtra(Proyecto.class.getName())) {
            proyecto = (Proyecto) i.getSerializableExtra(Proyecto.class.getName());
            if (proyecto.getId() != -1) {
                ProyectosController controller = new ProyectosController(
                        manager);
                try {
                    Message msg = new Message("Cargando...",
                            NotificationsFragment.Notification_Loading, true, 4);
                    sendNotification(msg);
                    proyecto = controller.getProyecto(proyecto.getId());
                    msg.closeMessage();
                    sendNotification(msg);
                } catch (DuplicatedIdException e) {
                    sendNotification(new Message("Error: Id duplicada",
                            NotificationsFragment.Notification_Error, false, 4));
                    e.printStackTrace();
                } catch (IdNotFoundException e) {
                    sendNotification(new Message("Error: No encontrada",
                            NotificationsFragment.Notification_Error, false, 4));
                    e.printStackTrace();
                }
            }
        } else {
            proyecto = new Proyecto();
        }

        datePikerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Date", 2000).show();
                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nombreEdit.setText(proyecto.getNombre());
        datePikerButton.setText(proyecto.getFechaInicio());
        descripcionEdit.setText(proyecto.getDescripcion());

        if (proyecto.getId() == -1) {
            saveButton.setText("Crear");
            saveButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (validateInputs()) {
                        fillProyect();
                        try {

                            ProyectosController controller = new ProyectosController(
                                    manager);
                            long id = controller.addProyecto(proyecto);
                            proyecto.setId(id);
                        } catch (DuplicatedIdException e) {
                            sendNotification(new Message(
                                    "Error: clave duplicada",
                                    NotificationsFragment.Notification_Error,
                                    false, 4));
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UniqueKeyException e) {
                            sendNotification(new Message(
                                    "Error: Nombre repetido",
                                    NotificationsFragment.Notification_Error,
                                    false, 4));
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (proyecto.getId()>0){
                            sendNotification(new Message(
                                    "Proyecto añadido",
                                    NotificationsFragment.Notification_Ok,
                                    false, 4));
                            setResult(RESULT_PROJECT_OK);
                        }
                        saveButton.setText("Modificar");

                    } else {
                        sendNotification(new Message(
                                "Revise los datos.(Campo vacio)",
                                NotificationsFragment.Notification_Warning,
                                false, 4));
                    }
                }
            });
        } else {
            sendNotification(new Message("",
                    NotificationsFragment.Notification_Close, false, 0));
            saveButton.setText("Modificar");
            saveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateInputs()) {
                        fillProyect();
                        ProyectosController controller = new ProyectosController(
                                manager);
                        try {
                            if (controller.saveProyecto(proyecto)) {
                                // Guardado correcto.
                                sendNotification(new Message(
                                        "Modificación correcta",
                                        NotificationsFragment.Notification_Ok,
                                        false, 4));
                            } else {
                                // guardado incorreto
                                sendNotification(new Message(
                                        "Modificación fallida",
                                        NotificationsFragment.Notification_Error,
                                        false, 4));
                            }
                        } catch (DuplicatedIdException e) {
                            sendNotification(new Message("Id duplicada",
                                    NotificationsFragment.Notification_Error,
                                    false, 4));
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IdNotFoundException e) {
                            sendNotification(new Message("Id not found",
                                    NotificationsFragment.Notification_Error,
                                    false, 4));
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UniqueKeyException e) {
                            sendNotification(new Message("Nombre repetido",
                                    NotificationsFragment.Notification_Error,
                                    false, 4));
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        sendNotification(new Message(
                                "Revise los datos.(Campo vacío)",
                                NotificationsFragment.Notification_Warning,
                                false, 4000));
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.proyect, menu);
        return true;
    }

    private void sendNotification(Message msg) {
        Intent myFilteredResponse = new Intent();
        myFilteredResponse.setAction(NotificationsFragment.intentFilter);
        myFilteredResponse.putExtra(NotificationsFragment.data, msg);
        sendBroadcast(myFilteredResponse);
    }




    private boolean validateInputs() {
        String nombre = nombreEdit.getText().toString();
        String descripcion = descripcionEdit.getText().toString();
        String date = datePikerButton.getText().toString();
        if (ValidateUtil.validateText(nombre) && ValidateUtil.validateText(descripcion)
                && ValidateUtil.validateDate(date)) {
            return true;
        }
        return false;
    }

    private void fillProyect() {
        proyecto.setDescripcion(descripcionEdit.getText().toString());
        proyecto.setFechaInicio(datePikerButton.getText().toString());
        proyecto.setNombre(nombreEdit.getText().toString());
    }
}
