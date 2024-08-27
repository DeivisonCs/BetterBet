package models;


import javax.swing.ImageIcon;

/**
 * A classe abstrata User representa um usuário no sistema.
 * Ela define as propriedades básicas e comportamentos comuns para diferentes tipos de usuários.
 */
public abstract class User {

    private Integer id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String permission;
    private ImageIcon profileImage;
    
    /**
     * Construtor padrão para a classe User.
     */
    public User(){}
    
    /**
     * Construtor da classe User que inicializa todos os atributos.
     * 
     * @param id Identificador único do usuário.
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param email Email do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário.
     */
    public User(Integer id, String name, ImageIcon profileImage, String cpf, String email, String password, String permission) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }
    
    /**
     * Construtor da classe User sem o atributo id.
     * 
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param email Email do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário.
     */
    public User(String name, ImageIcon profileImage, String cpf, String email, String password, String permission) {
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.permission = permission;
    }

    // Getters e Setters
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ImageIcon getProfileImage() { return this.profileImage; }
    public void setProfileImage(ImageIcon img) { this.profileImage = img; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    /**
     * Retorna uma representação em string do usuário.
     * 
     * @return String com o nome, email, senha e permissão do usuário.
     */
    @Override
    public String toString() {
        return "Name: " + this.name +
            " | Email: " + this.email +
            " | Password: " + this.password +
            " | Permission: " + this.permission;
    }
}