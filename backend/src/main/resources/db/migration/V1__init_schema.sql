DROP TABLE IF EXISTS alertas;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS transacoes;
DROP TABLE IF EXISTS portfolios;
DROP TABLE IF EXISTS historico_precos;
DROP TABLE IF EXISTS criptomoedas;
DROP TABLE IF EXISTS autenticacao;
DROP TABLE IF EXISTS usuarios;

DROP TYPE IF EXISTS perfil_tipo;
DROP TYPE IF EXISTS transacao_tipo;
DROP TYPE IF EXISTS role_tipo;

-- ENUMs
CREATE TYPE perfil_tipo AS ENUM ('iniciante', 'experiente', 'corporativo');
CREATE TYPE transacao_tipo AS ENUM ('compra', 'venda', 'deposito', 'saque');
CREATE TYPE role_tipo AS ENUM ('usuario', 'gestor', 'administrador');

-- USUARIOS
CREATE TABLE usuarios(
                    id BIGSERIAL PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    senha_hash TEXT NOT NULL,
                    perfil perfil_tipo NOT NULL,
                    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

-- AUTENTICACAO
CREATE TABLE autenticacao(
                        id BIGSERIAL PRIMARY KEY,
                        usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id),
                        token_2fa TEXT,
                        ativo_2fa BOOLEAN NOT NULL DEFAULT FALSE,
                        ultimo_login TIMESTAMP,
                        refresh_token TEXT,
                        CHECK (ativo_2fa = FALSE OR token_2fa IS NOT NULL)
);

-- CRIPTOMOEDAS
CREATE TABLE criptomoedas(
                        id BIGSERIAL PRIMARY KEY,
                        simbolo VARCHAR(10) NOT NULL UNIQUE,
                        nome VARCHAR(100) NOT NULL,
                        preco_atual NUMERIC(18,8) NOT NULL,
                        atualizado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

-- HISTORICO DE PRECOS
CREATE TABLE historico_precos(
                            id BIGSERIAL PRIMARY KEY,
                            cripto_id BIGINT NOT NULL REFERENCES criptomoedas(id),
                            preco NUMERIC(18,8) NOT NULL,
                            registrado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

-- PORTFOLIO
CREATE TABLE portfolios(
                        id BIGSERIAL PRIMARY KEY,
                        usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                        cripto_id BIGINT NOT NULL REFERENCES criptomoedas(id),
                        quantidade NUMERIC(18,8) NOT NULL,
                        atualizado_em TIMESTAMP NOT NULL DEFAULT NOW(),
                        UNIQUE(usuario_id, cripto_id),
                        CHECK (quantidade >= 0)
);

-- TRANSACOES
CREATE TABLE transacoes(
                        id BIGSERIAL PRIMARY KEY,
                        usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                        cripto_id BIGINT NOT NULL REFERENCES criptomoedas(id),
                        tipo transacao_tipo NOT NULL,
                        quantidade NUMERIC(18,8) NOT NULL,
                        preco_unitario NUMERIC(18,8) NOT NULL,
                        realizado_em TIMESTAMP NOT NULL DEFAULT NOW(),
                        CHECK (quantidade > 0),
                        CHECK (preco_unitario >= 0)
);

-- ROLES
CREATE TABLE roles(
                    id BIGSERIAL PRIMARY KEY,
                    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                    tipo role_tipo NOT NULL,
                    atribuido_em TIMESTAMP NOT NULL DEFAULT NOW(),
                    UNIQUE(usuario_id, tipo)
);

-- ALERTAS
CREATE TABLE alertas(
                    id BIGSERIAL PRIMARY KEY,
                    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                    cripto_id BIGINT NOT NULL REFERENCES criptomoedas(id),
                    preco_alvo NUMERIC(18,8) NOT NULL,
                    ativo BOOLEAN NOT NULL DEFAULT FALSE,
                    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
                    CHECK (preco_alvo > 0)
);