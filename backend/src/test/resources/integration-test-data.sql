-- Script específico para o teste de integração
-- Limpa e carrega dados de teste

-- Limpar dados existentes respeitando foreign keys
DELETE FROM linhas;
DELETE FROM orixas;

-- Inserir os Orixás principais da Umbanda com IDs específicos
INSERT INTO orixas (id, nome, nome_africano, descricao, cor_primaria, dia_semana, elemento, ativo) VALUES
(1, 'Oxalá', 'Obatalá', 'Pai de todos os Orixás, representa a paz e a sabedoria. É o criador do mundo e dos seres humanos, simbolizando a pureza, a calma e a serenidade. Suas energias trazem equilíbrio espiritual e mental.', '#FFFFFF', 'Sexta-feira', 'Ar', true),

(2, 'Ogum', 'Ogún', 'Guerreiro e protetor, abre caminhos e remove obstáculos. Senhor do ferro e da guerra, protetor dos guerreiros e trabalhadores. Suas energias trazem força, coragem e determinação para vencer as batalhas da vida.', '#008000', 'Terça-feira', 'Ferro', true),

(3, 'Oxóssi', 'Ọxọ́ṣì', 'Caçador da mata, senhor da abundância e da fartura. Protetor das florestas e dos animais, responsável pela caça e pela provisão de alimentos. Suas energias trazem prosperidade e conexão com a natureza.', '#0000FF', 'Quinta-feira', 'Terra', true),

(4, 'Xangô', 'Ṣàngó', 'Senhor da justiça e dos raios, rei poderoso e justo. Orixá do fogo, dos trovões e da justiça divina. Suas energias trazem equidade, liderança e o poder de fazer justiça quando necessário.', '#8B4513', 'Quarta-feira', 'Fogo', true),

(5, 'Iemanjá', 'Yemọja', 'Rainha do mar, mãe de todos os Orixás e dos seres humanos. Senhora das águas salgadas, protetora das mães e das crianças. Suas energias trazem maternidade, proteção e amor incondicional.', '#4169E1', 'Sábado', 'Água', true),

(6, 'Oxum', 'Ọ̀ṣun', 'Senhora do amor e da prosperidade, rainha das águas doces. Orixá da beleza, do amor, da fertilidade e da riqueza. Suas energias trazem harmonia nos relacionamentos e abundância material.', '#FFD700', 'Sábado', 'Água', true),

(7, 'Iansã', 'Ọya', 'Senhora dos ventos e tempestades, guerreira dos raios. Orixá dos ventos, das tempestades e do mundo dos mortos. Suas energias trazem transformação, renovação e coragem para enfrentar mudanças.', '#FF69B4', 'Quarta-feira', 'Ar', true);

-- Inserir as Linhas espirituais da Umbanda
INSERT INTO linhas (nome, orixa_regente_id, orixa_adjunto_id, descricao, caracteristicas, cores, elementos_trabalho, numero_ordem) VALUES
('Linha de Oxalá', 1, NULL, 'Linha da paz, sabedoria e criação divina', 'Trabalham com paz, harmonização, cura espiritual e evolução', 'Branco, Cristal', 'Velas brancas, flores brancas, água', 1),
('Linha de Iemanjá', 5, NULL, 'Linha das águas salgadas, maternidade e proteção', 'Trabalham com limpeza, proteção maternal, cura emocional', 'Azul claro, Branco', 'Água do mar, conchas, perfumes', 2),
('Linha de Ogum', 2, NULL, 'Linha da guerra santa, abertura de caminhos', 'Trabalham com demandas, abertura de caminhos, proteção', 'Verde, Vermelho', 'Espadas, ferramentas, cerveja', 3),
('Linha de Oxóssi', 3, NULL, 'Linha das matas, fartura e abundância', 'Trabalham com cura pelas ervas, fartura, caça ao mal', 'Verde, Azul', 'Arco e flecha, frutas, mel', 4),
('Linha de Xangô', 4, NULL, 'Linha da justiça, ordem e equilíbrio', 'Trabalham com justiça, organização, quebra de demandas', 'Marrom, Vermelho', 'Machado duplo, pedras, azeite dendê', 5),
('Linha de Iansã', 7, NULL, 'Linha dos ventos, tempestades e eguns', 'Trabalham com movimento, mudanças, eguns', 'Rosa, Roxo', 'Leque, espada, flores amarelas', 6),
('Linha das Águas', 6, 5, 'Linha de Oxum e Iemanjá unidas', 'Trabalham com amor, prosperidade, maternidade', 'Dourado, Azul', 'Águas, espelhos, jóias', 7),
('Linha de Exu', 7, NULL, 'Linha dos guardiões e mensageiros', 'Trabalham na porteira, guardião dos caminhos', 'Vermelho, Preto', 'Encruzilhadas, velas vermelhas e pretas', 8);
