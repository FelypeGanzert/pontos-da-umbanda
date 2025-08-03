
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

-- Inserir as Linhas espirituais da Umbanda
INSERT INTO linhas (nome, orixa_regente_id, orixa_adjunto_id, descricao, caracteristicas, cores, elementos_trabalho, numero_ordem) VALUES
('Linha de Oxalá', 1, NULL, 'Linha da paz, sabedoria e criação divina', 'Trabalham com paz, harmonização, cura espiritual e evolução', 'Branco, Cristal', 'Velas brancas, flores brancas, água', 1),
('Linha de Iemanjá', 5, NULL, 'Linha das águas salgadas, maternidade e proteção', 'Trabalham com limpeza, proteção maternal, cura emocional', 'Azul claro, Branco', 'Água do mar, conchas, perfumes', 2),
('Linha de Ogum', 2, NULL, 'Linha da guerra santa, abertura de caminhos', 'Trabalham com demandas, abertura de caminhos, proteção', 'Verde, Vermelho', 'Espadas, ferramentas, cerveja', 3),
('Linha de Oxóssi', 3, NULL, 'Linha das matas, fartura e abundância', 'Trabalham com cura pelas ervas, fartura, caça ao mal', 'Verde, Azul', 'Arco e flecha, frutas, mel', 4),
('Linha de Xangô', 4, NULL, 'Linha da justiça, ordem e equilíbrio', 'Trabalham com justiça, organização, quebra de demandas', 'Marrom, Vermelho', 'Machado duplo, pedras, azeite dendê', 5),
('Linha de Iansã', 7, NULL, 'Linha dos ventos, tempestades e eguns', 'Trabalham com movimento, mudanças, eguns', 'Rosa, Roxo', 'Leque, espada, flores amarelas', 6),
('Linha das Águas', 6, 5, 'Linha de Oxum e Iemanjá unidas', 'Trabalham com amor, prosperidade, maternidade', 'Dourado, Azul', 'Águas, espelhos, jóias', 7),
('Linha Mista', 1, 2, 'Linha de trabalhos diversos com Oxalá e Ogum', 'Trabalham com paz e proteção, caminhos abertos com sabedoria', 'Branco, Verde', 'Velas brancas e verdes, água e espada', 8);


-- Dados iniciais para a tabela finalidades
INSERT INTO finalidades (nome, descricao, categoria, uso_ritual, momento_gira, ativo) VALUES
('Abertura', 'Abertura dos trabalhos', null, null, null, true),
('Trabalho', 'Finalidade de trabalho', null, null, null, true),
('Cura', 'Finalidade de cura', null, null, null, true),
('Proteção', 'Finalidade de proteção', null, null, null, true),
('Fechamento', 'Fechamento dos trabalhos', null, null, null, true),
('Chegada', 'Chegada das entidades', null, null, null, true),
('Partida', 'Partida das entidades', null, null, null, true),
('Vibração', 'Vibração energética', null, null, null, true),
('Defumação', 'Defumação do ambiente', null, null, null, true),
('Descarrego', 'Descarrego espiritual', null, null, null, true),
('Fluidificação', 'Fluidificação de elementos', null, null, null, true),
('Demanda', 'Atuação em demandas', null, null, null, true),
('Oferenda', 'Oferenda ritualística', null, null, null, true);