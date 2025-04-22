package adapter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.Simbiosys.model.AcoesRealizadas;
import school.sptech.Simbiosys.model.Encaminhamento;
import school.sptech.Simbiosys.model.OutrosNumeros;
import school.sptech.Simbiosys.model.Relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioExcelAdapter {
    public static byte[] exportarParaExcel(List<Relatorio> relatorios) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Relatórios");

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            Row header = sheet.createRow(0);
            String[] colunas = {
                    "ID", "Mês/Ano", "Data Atualização", "Usuário",

                    // Encaminhamento
                    "Enc. BPC", "Enc. Aposentadoria", "Enc. Assistência Social", "Enc. Cursos Fora",
                    "Enc. Cursos Dentro", "Enc. Educação Não Formal", "Enc. Educação Formal", "Enc. Documentos",
                    "Enc. Trabalho", "Enc. Geração de Renda", "Enc. Saúde", "Enc. Trat. Drogas",
                    "Enc. Prog. Transf. Renda", "Enc. Políticas Públicas",

                    // Outros Números
                    "Alimentação", "Pessoas Presenciais", "Cestas Básicas", "Kits Higiene",
                    "Participantes Distância", "Participantes Presenciais",

                    // Ações Realizadas
                    "Ativ. Grupo Virtual", "Ativ. Cult. Externas", "Ativ. Cult. Virtuais",
                    "Palestras Presenciais", "Palestras Virtuais",
                    "Visitas Fam. Presenciais", "Visitas Fam. Virtuais",
                    "Visitas Mon. Presenciais", "Visitas Mon. Virtuais",
                    "Cursos Presenciais", "Cursos Virtuais",
                    "Pessoas Capacit. Presenciais", "Pessoas Capacit. Virtuais",
                    "Profiss. Presenciais", "Profiss. Virtuais"
            };

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            int colOffset = 4; // até col 3 são dados não somáveis

            // Array para somar valores
            int[] totais = new int[colunas.length];

            for (Relatorio r : relatorios) {
                Row row = sheet.createRow(rowIdx++);
                int col = 0;

                row.createCell(col++).setCellValue(r.getId());
                row.createCell(col++).setCellValue(r.getMesAno());
                row.createCell(col++).setCellValue(String.valueOf(r.getDataAtualizacao()));
                row.createCell(col++).setCellValue(r.getUsuario() != null ? r.getUsuario().getNome() : "-");

                Encaminhamento e = r.getEncaminhamento();
                totais[col - colOffset] += e != null ? e.getEncBeneficioPrestacaoContinuada() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncBeneficioPrestacaoContinuada() : 0);

                totais[col - colOffset] += e != null ? e.getEncAposentadoria() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncAposentadoria() : 0);

                totais[col - colOffset] += e != null ? e.getEncAssistenciaSocial() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncAssistenciaSocial() : 0);

                totais[col - colOffset] += e != null ? e.getEncCursosProfissionalizantesForaOrganizacao() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncCursosProfissionalizantesForaOrganizacao() : 0);

                totais[col - colOffset] += e != null ? e.getEncCursosProfissionalizantesDentroOrganizacao() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncCursosProfissionalizantesDentroOrganizacao() : 0);

                totais[col - colOffset] += e != null ? e.getEncEducacaoNaoFormal() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncEducacaoNaoFormal() : 0);

                totais[col - colOffset] += e != null ? e.getEncEducacaoFormal() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncEducacaoFormal() : 0);

                totais[col - colOffset] += e != null ? e.getEncDocumentos() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncDocumentos() : 0);

                totais[col - colOffset] += e != null ? e.getEncTrabalho() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncTrabalho() : 0);

                totais[col - colOffset] += e != null ? e.getEncGeracaoRenda() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncGeracaoRenda() : 0);

                totais[col - colOffset] += e != null ? e.getEncSaude() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncSaude() : 0);

                totais[col - colOffset] += e != null ? e.getEncTratamentoDrogas() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncTratamentoDrogas() : 0);

                totais[col - colOffset] += e != null ? e.getEncProgramasTransferenciaRenda() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncProgramasTransferenciaRenda() : 0);

                totais[col - colOffset] += e != null ? e.getEncPoliticasPublicas() : 0;
                row.createCell(col++).setCellValue(e != null ? e.getEncPoliticasPublicas() : 0);

                OutrosNumeros o = r.getOutrosNumeros();
                int[] outros = {
                        o != null ? o.getAlimentacao() : 0,
                        o != null ? o.getNumeroDePessoasPresencial() : 0,
                        o != null ? o.getCestasBasicasDoadas() : 0,
                        o != null ? o.getKitsHigieneDoados() : 0,
                        o != null ? o.getTotalParticipantesAtividadeDistancia() : 0,
                        o != null ? o.getTotalParticipantesAtividadePresencial() : 0
                };

                for (int i = 0; i < outros.length; i++) {
                    totais[col - colOffset] += outros[i];
                    row.createCell(col++).setCellValue(outros[i]);
                }

                AcoesRealizadas a = r.getAcoesRealizadas();
                int[] acoes = {
                        a != null ? a.getTotalAtividadesGrupoVirtual() : 0,
                        a != null ? a.getTotalAtividadesCulturaisExternas() : 0,
                        a != null ? a.getTotalAtividadesCulturaisVirtuais() : 0,
                        a != null ? a.getTotalPalestrasPresenciais() : 0,
                        a != null ? a.getTotalPalestrasVirtuais() : 0,
                        a != null ? a.getTotalVisitasFamiliaresPresenciais() : 0,
                        a != null ? a.getTotalVisitasFamiliaresVirtuais() : 0,
                        a != null ? a.getTotalVisitasMonitoradasPresenciais() : 0,
                        a != null ? a.getTotalVisitasMonitoradasVirtuais() : 0,
                        a != null ? a.getTotalCursosMinistradosPresenciais() : 0,
                        a != null ? a.getTotalCursosMinistradosVirtuais() : 0,
                        a != null ? a.getTotalPessoasCursosCapacitacaoPresenciais() : 0,
                        a != null ? a.getTotalPessoasCursosCapacitacaoVirtuais() : 0,
                        a != null ? a.getTotalPessoasCursosProfissionalizantesPresenciais() : 0,
                        a != null ? a.getTotalPessoasCursosProfissionalizantesVirtuais() : 0
                };

                for (int i = 0; i < acoes.length; i++) {
                    totais[col - colOffset] += acoes[i];
                    row.createCell(col++).setCellValue(acoes[i]);
                }
            }

            // Linha de totais
            Row totalRow = sheet.createRow(rowIdx);
            totalRow.createCell(0).setCellValue("TOTAL GERAL");
            for (int i = 0; i < totais.length; i++) {
                if (i >= colOffset) {
                    totalRow.createCell(i).setCellValue(totais[i - colOffset]);
                }
            }

            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
