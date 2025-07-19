-- Inserção de dados iniciais para a tabela orixas
-- Arquivo de dados para desenvolvimento com banco H2
-- Este arquivo será executado após o schema.sql

-- Inserir os Orixás principais da Umbanda
INSERT INTO orixas (nome, nome_africano, descricao, cor_primaria, dia_semana, elemento, ativo) VALUES
('Oxalá', 'Obatalá', 'Pai de todos os Orixás, representa a paz e a sabedoria. É o criador do mundo e dos seres humanos, simbolizando a pureza, a calma e a serenidade. Suas energias trazem equilíbrio espiritual e mental.', '#FFFFFF', 'Sexta-feira', 'Ar', true),

('Ogum', 'Ogún', 'Guerreiro e protetor, abre caminhos e remove obstáculos. Senhor do ferro e da guerra, protetor dos guerreiros e trabalhadores. Suas energias trazem força, coragem e determinação para vencer as batalhas da vida.', '#008000', 'Terça-feira', 'Ferro', true),

('Oxóssi', 'Ọxọ́ṣì', 'Caçador da mata, senhor da abundância e da fartura. Protetor das florestas e dos animais, responsável pela caça e pela provisão de alimentos. Suas energias trazem prosperidade e conexão com a natureza.', '#0000FF', 'Quinta-feira', 'Terra', true),

('Xangô', 'Ṣàngó', 'Senhor da justiça e dos raios, rei poderoso e justo. Orixá do fogo, dos trovões e da justiça divina. Suas energias trazem equidade, liderança e o poder de fazer justiça quando necessário.', '#8B4513', 'Quarta-feira', 'Fogo', true),

('Iemanjá', 'Yemọja', 'Rainha do mar, mãe de todos os Orixás e dos seres humanos. Senhora das águas salgadas, protetora das mães e das crianças. Suas energias trazem maternidade, proteção e amor incondicional.', '#4169E1', 'Sábado', 'Água', true),

('Oxum', 'Ọ̀ṣun', 'Senhora do amor e da prosperidade, rainha das águas doces. Orixá da beleza, do amor, da fertilidade e da riqueza. Suas energias trazem harmonia nos relacionamentos e abundância material.', '#FFD700', 'Sábado', 'Água', true),

('Iansã', 'Ọya', 'Senhora dos ventos e tempestades, guerreira dos raios. Orixá dos ventos, das tempestades e do mundo dos mortos. Suas energias trazem transformação, renovação e coragem para enfrentar mudanças.', '#FF69B4', 'Quarta-feira', 'Ar', true);

-- Verificar se os dados foram inseridos corretamente
-- SELECT COUNT(*) as total_orixas FROM orixas;
-- SELECT nome, nome_africano, elemento, dia_semana FROM orixas ORDER BY nome;
