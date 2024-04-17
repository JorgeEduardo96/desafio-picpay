INSERT INTO usuario (id, nome_completo, cpf_cnpj, email, senha, data_cadastro, tipo_usuario)
VALUES (
    '550e8400-e29b-41d4-a716-446655440001',
    'Fulano Souza',
    '98765432101',
    'fulano@example.com',
    'htcwp456!',
    '2022-03-02T14:30:00Z',
    'COMUM'
);

INSERT INTO usuario (id, nome_completo, cpf_cnpj, email, senha, data_cadastro, tipo_usuario)
VALUES (
    '550e8400-e29b-41d4-a716-446655440002',
    'Usuário 1',
    '12581161388',
    'usuario1@example.com',
    'htcwp789!',
    '2022-03-03T10:45:00Z',
    'COMUM'
);

INSERT INTO usuario (id, nome_completo, cpf_cnpj, email, senha, data_cadastro, tipo_usuario)
VALUES (
    '550e8400-e29b-41d4-a716-446655440003',
    'Usuário 2',
    '21807940373',
    'usuario2@example.com',
    'htcwp789!',
    '2022-03-03T10:45:00Z',
    'COMUM'
);

INSERT INTO usuario (id, nome_completo, cpf_cnpj, email, senha, data_cadastro, tipo_usuario)
VALUES (
    '550e8400-e29b-41d4-a716-446655440004',
    'Lojista Comércio Ltda',
    '11223344000100',
    'lojista@example.com',
    'htcwp123',
    '2022-03-04T09:15:00Z',
    'LOJISTA'
);

INSERT INTO usuario (id, nome_completo, cpf_cnpj, email, senha, data_cadastro, tipo_usuario)
VALUES (
    '40b6b133-7c2e-4eae-ad8e-cb3c3080e327',
    'Jorge Eduardo',
    '57106858528',
    'jorge@example.com',
    'htcwp789!',
    '2022-03-04T09:15:00Z',
    'COMUM'
);

INSERT INTO transferencia (id, usuario_transferencia_id, usuario_recebimento_id, valor_transferencia, data_transferencia, notificacao_enviada)
VALUES (
    '0d0933b1-9bd6-4ea4-94fc-0b97f0a2c289',
    '550e8400-e29b-41d4-a716-446655440002',
    '550e8400-e29b-41d4-a716-446655440001',
    20000.00,
    '2022-03-04T10:30:00Z',
    FALSE
);

INSERT INTO transferencia (id, usuario_transferencia_id, usuario_recebimento_id, valor_transferencia, data_transferencia, notificacao_enviada)
VALUES (
    '46e5844c-03a3-4ee1-8e2e-df61ef115fe4',
    '550e8400-e29b-41d4-a716-446655440003',
    '550e8400-e29b-41d4-a716-446655440001',
    50.00,
    '2022-03-04T10:30:00Z',
    FALSE
);