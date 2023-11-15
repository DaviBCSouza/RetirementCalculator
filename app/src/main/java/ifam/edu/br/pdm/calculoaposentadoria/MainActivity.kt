package ifam.edu.br.pdm.calculoaposentadoria

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Definindo o arquivo layout
        setContentView(R.layout.activity_main)

        // Spinner
        val spinnerSexo = findViewById<Spinner>(R.id.spinner_sexo)

        // Typeface
        val typeface = ResourcesCompat.getFont(this, R.font.chewy)

        // Caixa de Idade
        val editTextIdade = findViewById<EditText>(R.id.edit_text_idade)

        // Caixa de Contribuição
        val editTextContrib = findViewById<EditText>(R.id.edit_text_contrib)

        // Botão
        val buttonCalcular = findViewById<Button>(R.id.button_calcular)

        // TextView Resultado
        val textViewResultado = findViewById<TextView>(R.id.text_view_resultado)

        // Criando um ArrayAdapter Personalizado
        val sexoAdapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.sexo_options)
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.typeface = typeface
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.typeface = typeface
                return view
            }
        }

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

            // Obter a contribuição inserida
            val contribuicaoTexto = editTextContrib.text.toString()
            val contribuicao = contribuicaoTexto.toIntOrNull()

            // Verificando se a idade e contribuição são números válidos
            if (idade != null && contribuicao != null) {
                val idadeAposentadoria = if (sexoSelecionado == "Masculino") 65 else 62

                val anosRestantesIdade = idadeAposentadoria - idade
                val anosRestantesContribuicao = 15 - contribuicao

                val mensagem = when {
                    // Pessoa já aposentada
                    idade >= idadeAposentadoria && contribuicao >= 15 -> getString(R.string.mensagem_ja_aposentado)

                    // Pessoa com contribuição ok, mas idade não
                    idade <= idadeAposentadoria && contribuicao >= 15 -> getString(
                        R.string.mensagem_faltam_anos,
                        anosRestantesIdade
                    )

                    // Pessoa com idade ok, mas contribuição não
                    idade >= idadeAposentadoria && contribuicao < 15 -> getString(
                        R.string.mensagem_faltam_anos,
                        anosRestantesContribuicao
                    )

                    // Pessoa com idade e contribuição não suficientes
                    else -> getString(
                        R.string.mensagem_faltam_anos,
                        anosRestantesIdade + anosRestantesContribuicao
                    )
                }

                textViewResultado.text = mensagem

            } else {
                textViewResultado.text = getString(R.string.mensagem_dados_invalidos)
            }
        }
    }
}