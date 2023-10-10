package ifam.edu.br.pdm.calculoaposentadoria

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Definindo o arquivo layout
        setContentView(R.layout.activity_main)

        // Spinner
        val spinnerSexo = findViewById<Spinner>(R.id.spinner_sexo)

        // Caixa de Idade
        val editTextIdade = findViewById<EditText>(R.id.edit_text_idade)

        // Botão
        val buttonCalcular = findViewById<Button>(R.id.button_calcular)

        // TextView Resultado
        val textViewResultado = findViewById<TextView>(R.id.text_view_resultado)

        // Criando um ArrayAdapter com as opções "Masculino" e "Feminino"
        val sexoAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.sexo_options,
            android.R.layout.simple_spinner_dropdown_item
        )

        // Especificando o layout a ser usado quando a lista aparecer
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Definindo o adaptador no Spinner
        spinnerSexo.adapter = sexoAdapter

        // Definindo uma ação de clique para o botão
        buttonCalcular.setOnClickListener {
            // Obter o sexo selecionado no Spinner
            val sexoSelecionado = spinnerSexo.selectedItem.toString()

            // Obter a idade inserida no EditText
            val idadeTexto = editTextIdade.text.toString()
            val idade = idadeTexto.toIntOrNull()

            // Verificando se a idade é um número válido
            if (idade != null) {
                val idadeAposentadoria = if (sexoSelecionado == "Masculino") 65 else 62
                val anosRestantes = idadeAposentadoria - idade

                if (anosRestantes > 0) {
                    val mensagem = getString(R.string.mensagem_faltam_anos, anosRestantes)
                    textViewResultado.text = mensagem
                } else {
                    textViewResultado.text = getString(R.string.mensagem_ja_aposentado)
                }
            } else {
                textViewResultado.text = getString(R.string.mensagem_idade_invalida)
            }
        }
    }
}