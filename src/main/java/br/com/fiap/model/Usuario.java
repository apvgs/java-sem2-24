package br.com.fiap.model;

import br.com.fiap.exception.CpfInvalido;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private Long id;
    private String cpf;
    private String nome;
    private Login login;

    public Usuario(String cpf, String nome, Login login) throws CpfInvalido {
        setCpf(cpf);
        this.nome = nome;
        this.login = login;
    }

    public Usuario() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws CpfInvalido {
        validaCpf(cpf);
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private void validaCpf(String cpf) throws CpfInvalido {
        cpf = RemoveSpecialChar(cpf);
        if (!ValidateCPFString(cpf)) {
            String[] str = cpf.split("");
            List<Integer> lastTwoCPF = new ArrayList<>();
            int result1 = 0;
            int result2 = 0;

            // AJEITANDO ARRAY
            for (int i = str.length - 2; i < str.length; i++) {
                lastTwoCPF.add(Integer.parseInt(str[i]));
            }

            // CASO 1
            for (int i = 0; i < str.length - 2; i++) {
                result1 += Integer.parseInt(str[i]) * (i + 1);
            }

            // CASO 2
            for (int i = 0; i < str.length - 1; i++) {
                result2 += Integer.parseInt(str[i]) * i;
            }

            // VALIDACAO FINAL
            int digit1 = ValidateResult(result1);
            int digit2 = ValidateResult(result2);

            if (ValidateCPFDigits(lastTwoCPF, digit1, digit2)) {
                this.cpf = cpf;
            } else {
                throw new CpfInvalido("CPF inválido!");
            }
        } else {
            throw new CpfInvalido("CPF inválido!");
        }
    }

    private Integer ValidateResult(Integer result) {
        return result % 11 == 10 ? 0 : result % 11;
    }

    private boolean ValidateCPFDigits(List<Integer> lastTwo, int d1, int d2) {
        return lastTwo.contains(d1) && lastTwo.contains(d2);
    }

    private boolean ValidateCPFString(String cpf) {
        return cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999") || (cpf.length() != 11);
    }

    private String RemoveSpecialChar(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }
}
