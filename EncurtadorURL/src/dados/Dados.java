
package dados;

import java.util.HashMap;

import model.Usuario;
import model.URL;

public class Dados {

    private HashMap<String, Usuario> usuarios;
    private HashMap<String, URL> urls;

    /** inicializa os hashmap e cadastra o usuario*/
    public Dados() {

        usuarios = new HashMap<>();
        urls = new HashMap<>();

        usuarios.put("admin", new Usuario("admin", "1234"));
    }

    /** metodo para verificar se o usuario e senha estao corretos*/
    public boolean autenticar(String nome, String senha) {

        Usuario usuario = usuarios.get(nome);

        return usuario != null && usuario.getSenha().equals(senha);
    }

    /** metodo que procura uma URL pelo codigo encurtado  */
    public URL buscarURL(String codigo) {
        return urls.get(codigo);
    }

    /** armazena um objeto no hashmap*/
    public void salvarURL(URL url) {
        urls.put(url.getCodigoEncurtado(), url);
    }

    /** metodo para verificar se o codigo ja foi cadastrado */
    public boolean existeCodigo(String codigo) {
        return urls.containsKey(codigo);
    }
}
