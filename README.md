# 🧾 Sistema de Gestão (Clientes, Produtos e Pedidos)

## 🔗 Tutoriais Importantes

📌 Como baixar e executar o Tomcat  
➡️ [Issue - Instalação e Execução do Tomcat](https://github.com/Khaldewey/java-web-sergipe-tec-challenge/issues/1)

📌 Como gerar WAR e fazer Deploy  
➡️ [Issue - Deploy da Aplicação no Tomcat](https://github.com/Khaldewey/java-web-sergipe-tec-challenge/issues/3)

---

## 📌 Sobre o Projeto

Sistema web desenvolvido com Java puro utilizando Servlets e JSP para gerenciamento de:

- 👤 Clientes
- 📦 Produtos
- 🧾 Pedidos

O projeto tem fins didáticos e demonstra a construção de um CRUD completo utilizando arquitetura MVC simplificada.

---

## 🛠️ Stack Tech

- **Java 17**
- **Maven**
- **Jakarta Servlet API**
- **JSP**
- **JSTL**
- **PostgreSQL**
- **Apache Tomcat 10**
- HTML5
- CSS3
- JavaScript

---

## 🏗️ Arquitetura Utilizada

O projeto segue o padrão:
- Model
- DAO
- Servlet (Controller)
- JSP (View)
### 📂 Camadas

- **Model** → Entidades do sistema (Cliente, Produto, Pedido)
- **DAO** → Acesso ao banco de dados
- **Servlet** → Controle de requisições HTTP
- **JSP** → Renderização das páginas
- **JSTL** → Iterações e condicionais nas views


---

## 🗄️ Script SQL - Criação do Banco (SGDB: PostgreSQL)

### 🔹 Criar banco

```sql
CREATE DATABASE empresa_web;

```
### 🔹 Criar tabelas
```sql
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(15,2) NOT NULL,
    estoque INTEGER NOT NULL,
    data_cadastro DATE NOT NULL
);


CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    data_cadastro DATE NOT NULL
);


CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    data_pedido DATE NOT NULL,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes(id)
        ON DELETE RESTRICT
);

CREATE TABLE pedido_item (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    valor_unitario_produto NUMERIC(15,2) NOT NULL,
    quantidade INTEGER NOT NULL,
    desconto NUMERIC(15,2) DEFAULT 0,

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id)
        ON DELETE RESTRICT
);
```
##  ▶️ Como Executar o Projeto
### 1️⃣ Clonar o repositório
```sql
git clone git@github.com:Khaldewey/java-web-sergipe-tec-challenge.git
````
### 2️⃣ Executar os scripts SQL acima para criar o banco e as tabelas

### 3️⃣ Compilar o projeto

```bash
mvn clean package
```
O arquivo .war será gerado em: 

/target

⚠️ Caso não tenha visualizado é importante que a partir daqui as issues abaixo sejam consultadas:

📌 Como baixar e executar o Tomcat  
➡️ [Issue - Instalação e Execução do Tomcat](https://github.com/Khaldewey/java-web-sergipe-tec-challenge/issues/1)

📌 Como gerar WAR e fazer Deploy  
➡️ [Issue - Deploy da Aplicação no Tomcat](https://github.com/Khaldewey/java-web-sergipe-tec-challenge/issues/3)

### Após configurar servidor tomcat e fazer deploy do WAR acesse o link da página principal da aplicação:
http://localhost:8080/empresa-web/pagina-principal

## Imagens

<img width="1361" height="414" alt="image" src="https://github.com/user-attachments/assets/942f5c96-1366-400a-a96a-ad46b8ef5b06" />

<img width="1362" height="507" alt="image" src="https://github.com/user-attachments/assets/ab86bdcd-e222-41d6-b0ba-b14b86f7af94" />

<img width="1350" height="515" alt="image" src="https://github.com/user-attachments/assets/1dd2c390-f7a0-4297-9789-3fa083c4158a" />

<img width="1288" height="595" alt="image" src="https://github.com/user-attachments/assets/92f9beba-4030-4c51-9585-6fd6a5b81a80" />

<img width="1082" height="586" alt="image" src="https://github.com/user-attachments/assets/81552412-a9bc-4048-bcc5-48d5cdeeaec0" />

<img width="1314" height="480" alt="image" src="https://github.com/user-attachments/assets/9d9ce69c-f18b-4816-b662-cc8bfd2be2e7" />

<img width="1313" height="582" alt="image" src="https://github.com/user-attachments/assets/223225ea-3203-464c-bc7c-fb7f07743754" />

<img width="642" height="567" alt="image" src="https://github.com/user-attachments/assets/ad20fa95-4e2e-40cd-a358-118d90423b47" />

## Diagramas

[![](https://img.plantuml.biz/plantuml/svg/nLRBRjim4BmRy3yGljI98w6z6eMHrd9R0qNg9dsSD-BM84g93advKkJVIrAaH4jCt9voatQ7kxF38QLhfK7gfkBJYMQQ8oaOx2LKG2YIDGUbK96CRAg3H0Na9zvF9zF90ShVi4ToB_DIL3cssRmI5BaYVwOJGagxrbHWM6jiGyGK-YpgFM4qlDvfoKoW5XKEGbW1uo5MWAb1GSCQA2WjHPjOa3tgBPrVaAKh74LlJ4KNRyj5cOqjd4uLKGU7i7rj_j4E7MRQIK4RBSxDH56LafKWGlmzsnTOiWeuEG8NCiItHg0zIlBGGAqP1OgRQgEqU6XmD7-1_wn04VgdP_rXARXKpojAVnsJSM1FTYmAKaQVriIzbgqPjaEbVFmcCa4tS5ktAyEKVjk6jXgh5M4QQtLMYdLed4f-4nfuGeZ4UBRdyoCUMjmWs1fcDBUFntCFFVAzPkPyCk4zzxHb4WMi0KMjppjb5_YbahqH4ll_-CnbG44aReBYtPUWu88eu4UGSv-ovOyY2BeWdDcxgULazzt3LY5_twWIv4v8GzoIja2F6K7STT3AdgoNEk0fgZvrZgg7FKlrP4iZI7zk1tpZeWbp9kcwp91jssejgW-Ccvj4pVCyxxku9Htp4dZPS93kb1XsNhHkYk7baVdMMtimvsjHrrXg9kehvwkhqGcEkPqnb49vvAWJfhgBCvP3P3OAdSHKV4Gz_wJrmI-uHNEjANr5JcBg86g55_sgdL3_jopbfX77o6Klpz5jo9XYPopQVyOYRSlkCJ7KeNjyYTdSUA_8BN8eZVsWCaI5-LBuxzJipOnSPXcPlSxporbylI-9lJInwg1vlkf-OvP4uY-Kc2aayoC3iYqk0j2jjk3mVv06JoS3qsRPAjPqIHe5fkQfQomeqZQ0JhRDOReTUnAIw3KkCseLK3RaXcp5MPeldbJcJ6CQu2NjGL7tKs6ZEdtpKpbxo7HoZJLrVwr_0G00)](https://editor.plantuml.com/uml/nLRBRjim4BmRy3yGljI98w6z6eMHrd9R0qNg9dsSD-BM84g93advKkJVIrAaH4jCt9voatQ7kxF38QLhfK7gfkBJYMQQ8oaOx2LKG2YIDGUbK96CRAg3H0Na9zvF9zF90ShVi4ToB_DIL3cssRmI5BaYVwOJGagxrbHWM6jiGyGK-YpgFM4qlDvfoKoW5XKEGbW1uo5MWAb1GSCQA2WjHPjOa3tgBPrVaAKh74LlJ4KNRyj5cOqjd4uLKGU7i7rj_j4E7MRQIK4RBSxDH56LafKWGlmzsnTOiWeuEG8NCiItHg0zIlBGGAqP1OgRQgEqU6XmD7-1_wn04VgdP_rXARXKpojAVnsJSM1FTYmAKaQVriIzbgqPjaEbVFmcCa4tS5ktAyEKVjk6jXgh5M4QQtLMYdLed4f-4nfuGeZ4UBRdyoCUMjmWs1fcDBUFntCFFVAzPkPyCk4zzxHb4WMi0KMjppjb5_YbahqH4ll_-CnbG44aReBYtPUWu88eu4UGSv-ovOyY2BeWdDcxgULazzt3LY5_twWIv4v8GzoIja2F6K7STT3AdgoNEk0fgZvrZgg7FKlrP4iZI7zk1tpZeWbp9kcwp91jssejgW-Ccvj4pVCyxxku9Htp4dZPS93kb1XsNhHkYk7baVdMMtimvsjHrrXg9kehvwkhqGcEkPqnb49vvAWJfhgBCvP3P3OAdSHKV4Gz_wJrmI-uHNEjANr5JcBg86g55_sgdL3_jopbfX77o6Klpz5jo9XYPopQVyOYRSlkCJ7KeNjyYTdSUA_8BN8eZVsWCaI5-LBuxzJipOnSPXcPlSxporbylI-9lJInwg1vlkf-OvP4uY-Kc2aayoC3iYqk0j2jjk3mVv06JoS3qsRPAjPqIHe5fkQfQomeqZQ0JhRDOReTUnAIw3KkCseLK3RaXcp5MPeldbJcJ6CQu2NjGL7tKs6ZEdtpKpbxo7HoZJLrVwr_0G00)

![Diagrama](https://img.plantuml.biz/plantuml/svg/dLNBRjim4BppAnOvoK3itWsuY5yeNAID4Jkt0i6MNAT49T8cgQI_q_PG842_GZ_M9IMhaXhqa9CUd9tTcLsjhdn05yeY5q67d62byS5XWQ08TdGggsSZDS8ObavZzQlwQUF9bfHMLWYKmJguyoKwRG4zN2npJIOGP0jddpovqOKuSW8jzH5DO4htc5D8q9gaUTE7rKST56GVhA9ymBQQttGm_DG_NbfZwgBuXcJGra3sVhLO3caM2RL08vEw5IhhBuIOpM1Zgj-IfN45MsSbUPyGBvW1ivaGPoV6RrkbKzZUxFRmvfXG_fF9P6tHHbs23cJyP38PHIVr8mOwnmbn9kXnhRy5Xze1br_zA2Wuwq4XEEwKzZgL98nb6gSVlWImXvOFuBfwXaxkexEg39Pl-UnKiX5QeQ9BbkIbDS5U0WKv4MGKHCfUHOtcAHXwQXwwkXG6d8SIyuwmPcQQo75FLcsEK3H4YzpQ8spH8KXaeMnO4PjniAuey-excytL0xneLkoIQvWs73X4jYBgz9C72flOa6mqGQMoc6iKvVUKnvcRWiVy4LrM1tP4ygaG4SB8D7uCfJ1ddvUsA7J83fXxwWH4a4Z8SKtUDI4TdkbuIgUm-RXRt-xvihzfglR0yzCfO24OE6uBlIN-GWt2PfLck5RyZXpsxUQmeTK3t_vVnNrgJAmaOhkqARQjA3tsyyOt_sIuswxc-_LvHZtirdiW7-of99YrTsCu1VMwhAcjcFD6AjDNtQsWQPoiEpRgawUUn3UzfWqI911nfs7jt4lSpkRvPvHVNy_USWxsWwHZN7Irm06Wr_4ugPsvFg-kE39AErwNxSw2x5hpR-BlhlEZBbExiQuuP_oJY3y0)

[![Diagrama](https://img.plantuml.biz/plantuml/svg/PLFTQeCm5BuNV8T3hooitRtWw6nN66pjQBkxmJZeeGGqwP9ejyVPxLwZBxP4ZOtsIdFo_Ppp9LaeZL9NPH46ckc2OCNmAB54o0dsz5LT_dZ646QmBgflOQlBWX7N1D4ISrHQYcaOX05cMaXuLnLA9W0LJCp_vTSkebIAio8vdOJ1oRYnZ9sGQoClwu9q0-v4kz88-2foAdmOH4rfBBXwtFeeinm1rasNFor8S4wPPe9trR5Osi1Iv5apzWfpeIPsr2G1DxhTs8XQzA4aYKMu66OF_P0nl6tt1xZFMg3wu14N9Tq1bSYAEyXHenUeQOdLgCdnhQgJCgfj8Z5mEZlhMpbVfY64GSkR3VhgEWg3dCRmWQTDDmQ5HOqowaY-XTct30ipQ1Tr34VIrwmZdz5Xmi21PxtDbJ7egsVqFkQOOdZUx9zs1_CvRDqu2cu3-SnSm32Vp_tMKwENUlRxAYEbnC3RWZhRQtv3H9k0bvcx237iA6VIJ7Iz0H2zCFFwZbHE1ohUYI-8vytR_0S0)](https://editor.plantuml.com/uml/PLFTQeCm5BuNV8T3hooitRtWw6nN66pjQBkxmJZeeGGqwP9ejyVPxLwZBxP4ZOtsIdFo_Ppp9LaeZL9NPH46ckc2OCNmAB54o0dsz5LT_dZ646QmBgflOQlBWX7N1D4ISrHQYcaOX05cMaXuLnLA9W0LJCp_vTSkebIAio8vdOJ1oRYnZ9sGQoClwu9q0-v4kz88-2foAdmOH4rfBBXwtFeeinm1rasNFor8S4wPPe9trR5Osi1Iv5apzWfpeIPsr2G1DxhTs8XQzA4aYKMu66OF_P0nl6tt1xZFMg3wu14N9Tq1bSYAEyXHenUeQOdLgCdnhQgJCgfj8Z5mEZlhMpbVfY64GSkR3VhgEWg3dCRmWQTDDmQ5HOqowaY-XTct30ipQ1Tr34VIrwmZdz5Xmi21PxtDbJ7egsVqFkQOOdZUx9zs1_CvRDqu2cu3-SnSm32Vp_tMKwENUlRxAYEbnC3RWZhRQtv3H9k0bvcx237iA6VIJ7Iz0H2zCFFwZbHE1ohUYI-8vytR_0S0)

[![Diagrama](https://img.plantuml.biz/plantuml/svg/VPFHIiCm58Rl2ts7mwuwsEPzOQBT50HrukQT88VaC09TiYLfrSVnrjVmnKpQfakBUDKc-Sv__lCtlJ8MjQqEPPfOOKk2jS2znWC29zZIgVhvbamWpE6sh3wKtttIYbTMGRP2ZiPgDKsJD45cbOONKw4M2j30nBt_VFb5LcZrRaXF9sboTDq44qUKridhkYJRm9rejpK27nIdCiOWQxR6WklhJKovvGYuRLn-Dj1AIKdC2YMxtR5OqM05IjQClKQkpCIFkbn26DqVFAfQzQ4ibvu8CSml-o5pUDfiTt1nR47pAZDEXcd1KCsWnbBf6Pmidm57Yr6qpfoeqL9Gylf1rEctsUGWwJsO-4yu5cnAqwHLc0-yTYxJXDCO7tJtYUTWiAnHPrrHtCAT-mgFEJh4dyEUx3d_BAxec3G9yBnlSwuO-Eeh-Zxkq-LmzxYzUTwvnsuJnZ7mHpHah3fLrCRpnaBmi5Wiua4Afrv4PhOL8sE62NceCt5ESqYqUKG9XgkImpDneTryvpi2sRrm_-UU3jE1IdGJhaZovk_z1G00)](https://editor.plantuml.com/uml/VPFHIiCm58Rl2ts7mwuwsEPzOQBT50HrukQT88VaC09TiYLfrSVnrjVmnKpQfakBUDKc-Sv__lCtlJ8MjQqEPPfOOKk2jS2znWC29zZIgVhvbamWpE6sh3wKtttIYbTMGRP2ZiPgDKsJD45cbOONKw4M2j30nBt_VFb5LcZrRaXF9sboTDq44qUKridhkYJRm9rejpK27nIdCiOWQxR6WklhJKovvGYuRLn-Dj1AIKdC2YMxtR5OqM05IjQClKQkpCIFkbn26DqVFAfQzQ4ibvu8CSml-o5pUDfiTt1nR47pAZDEXcd1KCsWnbBf6Pmidm57Yr6qpfoeqL9Gylf1rEctsUGWwJsO-4yu5cnAqwHLc0-yTYxJXDCO7tJtYUTWiAnHPrrHtCAT-mgFEJh4dyEUx3d_BAxec3G9yBnlSwuO-Eeh-Zxkq-LmzxYzUTwvnsuJnZ7mHpHah3fLrCRpnaBmi5Wiua4Afrv4PhOL8sE62NceCt5ESqYqUKG9XgkImpDneTryvpi2sRrm_-UU3jE1IdGJhaZovk_z1G00)


