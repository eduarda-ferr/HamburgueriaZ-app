package com.example.hamburgueriaz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {   private int quantidade = 0;
    private TextView textQuantidade, textResumo, textPreco;
    private EditText editNome;
    private CheckBox checkboxBacon, checkboxQueijo, checkboxOnion;

    private final int PRECO_BASE = 20;
    private final int PRECO_BACON = 2;
    private final int PRECO_QUEIJO = 2;
    private final int PRECO_ONION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textQuantidade = findViewById(R.id.textQuantidade);
        textResumo = findViewById(R.id.textResumo);
        textPreco = findViewById(R.id.textPreco);
        editNome = findViewById(R.id.editNome);
        checkboxBacon = findViewById(R.id.checkboxBacon);
        checkboxQueijo = findViewById(R.id.checkboxQueijo);
        checkboxOnion = findViewById(R.id.checkboxOnion);
        Button btnMais = findViewById(R.id.btnMais);
        Button btnMenos = findViewById(R.id.btnMenos);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnMais.setOnClickListener(v -> somar());
        btnMenos.setOnClickListener(v -> subtrair());
        btnEnviar.setOnClickListener(v -> enviarPedido());
    }
    private void somar() {
        quantidade++;
        atualizarQuantidade();
    }
    private void subtrair() {
        if (quantidade > 0) {
            quantidade--;
        }
        atualizarQuantidade();
    }
    private void atualizarQuantidade() {
        textQuantidade.setText(String.valueOf(quantidade));
    }
    private int calcularPrecoTotal() {
        int precoTotal = quantidade * PRECO_BASE;

        if (checkboxBacon.isChecked()) {
            precoTotal += quantidade * PRECO_BACON;
        }
        if (checkboxQueijo.isChecked()) {
            precoTotal += quantidade * PRECO_QUEIJO;
        }
        if (checkboxOnion.isChecked()) {
            precoTotal += quantidade * PRECO_ONION;
        }
        return precoTotal;
    }
    private void enviarPedido() {
        String nomeCliente = editNome.getText().toString().trim();
        if (nomeCliente.isEmpty()) {
            textResumo.setText("Por favor, insira seu nome.");
            return;
        }

        boolean temAdicional = checkboxBacon.isChecked() || checkboxQueijo.isChecked() || checkboxOnion.isChecked();
        if (!temAdicional) {
            textResumo.setText("Você não selecionou nenhum adicional.");
            return;
        }

        String temBacon = checkboxBacon.isChecked() ? "Sim" : "Não";
        String temQueijo = checkboxQueijo.isChecked() ? "Sim" : "Não";
        String temOnion = checkboxOnion.isChecked() ? "Sim" : "Não";
        int precoFinal = calcularPrecoTotal();

        String resumoPedido = " _Resumo do Pedido_ \n\n" +
                "Nome do Cliente: " + nomeCliente + "\n" +
                "Tem Bacon? " + temBacon + "\n" +
                "Tem Queijo? " + temQueijo + "\n" +
                "Tem Onion Rings? " + temOnion + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Preço Final: R$ " + precoFinal;

        textResumo.setText(resumoPedido);
        textPreco.setText("R$ " + precoFinal);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"emaildestino@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nomeCliente + "HamburgueriaZ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, resumoPedido);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Escolha um app de e-mail"));
        } else {
            textResumo.setText("Nenhum aplicativo de e-mail encontrado.");
        }
    }
}
