package br.univ.pharmasys.model;

import br.univ.pharmasys.service.DependenteValidador;
import br.univ.pharmasys.util.ValidadorUtils;

import java.time.LocalDate;

public class Dependente {

    private long id;
    private String cpf;
    private Funcionario funcionario; 
    private Long funcionarioId;
    private String cpfFuncionario; 
    private String nome;
    private String sexo;
    private String parentesco;
    private LocalDate dataNascimento;

    public Dependente() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {

        DependenteValidador.validarFuncionario(funcionario);
        this.funcionario = funcionario;
        this.funcionarioId = funcionario.getIdFuncionario();

        //Ao setar o objeto já preenche o CPF automaticamente

        this.cpfFuncionario = funcionario.getCpf();

    }

    public Long getFuncionarioId() {
        if (funcionarioId != null) return funcionarioId;
        return funcionario != null ? funcionario.getIdFuncionario() : null;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null) {
            ValidadorUtils.cpfValido(cpf);
            this.cpf = cpf.replaceAll("\\D", "");
        } else {
            this.cpf = null;
        }
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        //Chama a validação no serviço
        ValidadorUtils.cpfValido(cpfFuncionario);
        this.cpfFuncionario = cpfFuncionario.trim();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        DependenteValidador.validarNome(nome);
        this.nome = nome.trim();
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        DependenteValidador.validarParentesco(parentesco);
        this.parentesco = parentesco.trim();
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        DependenteValidador.validarDataNascimento(dataNascimento);
        this.dataNascimento = dataNascimento;
    }
    
    @Override
    public String toString() {
        return "Dependente [Nome=" + nome + ", Titular(CPF)=" + cpfFuncionario + "]";
    }
}