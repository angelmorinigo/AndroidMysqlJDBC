package com.example.sg.mysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    String user,ip,password,database,sql,field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    Thread sqlThread = new Thread() {
        public void run() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                // "jdbc:mysql://IP:PUERTO/DB", "USER", "PASSWORD");
                // Si estás utilizando el emulador de android y tenes el mysql en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
                Connection conn = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://"+ip+":3306/"+database+"", user, password);
                //En el stsql se puede agregar cualquier consulta SQL deseada.
                String stsql = sql;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(stsql);

                 if(rs.first()){do{ NewActivity(rs.getString(field));}while(rs.next());}
                 conn.close();
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
            }
        }
    };
    public void  Consultar(View view){sqlThread.start();
        EditText editText = (EditText) findViewById(R.id.ip);
        ip =  editText.getText().toString();

        EditText editText1 = (EditText) findViewById(R.id.user);
        user =  editText1.getText().toString();

        EditText editText2 = (EditText) findViewById(R.id.pass);
        password =  editText2.getText().toString();

        EditText editText3 = (EditText) findViewById(R.id.db);
        database =  editText3.getText().toString();

        EditText editText5 = (EditText) findViewById(R.id.sql);
        sql =  editText5.getText().toString();

        EditText editText6 = (EditText) findViewById(R.id.field);
        field =  editText6.getText().toString();


    }
    public void NewActivity(String user){
        Intent i=new Intent(this, Resultados.class);
        i.putExtra("user", user);
        startActivity(i);


    }
}
