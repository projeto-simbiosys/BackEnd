package adapter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.Simbiosys.model.AcoesRealizadas;
import school.sptech.Simbiosys.model.Encaminhamento;
import school.sptech.Simbiosys.model.OutrosNumeros;
import school.sptech.Simbiosys.model.Relatorio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioExcelAdapter {

    public static byte[] exportarParaExcel(List<Relatorio> relatorios) throws IOException {
        try (Workbook workbook = gerarWorkbook(relatorios);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            return out.toByteArray();
        }
    }

    public static String gerarRelatorioExcel(List<Relatorio> relatorios, String nomeArquivo) throws IOException {
        Workbook workbook = gerarWorkbook(relatorios);

        String caminhoPasta = "/tmp/relatorios/";
        File pasta = new File(caminhoPasta);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        String caminhoCompleto = caminhoPasta + nomeArquivo;
        try (FileOutputStream fileOut = new FileOutputStream(caminhoCompleto)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }

        return nomeArquivo;
    }

    private static Workbook gerarWorkbook(List<Relatorio> relatorios) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatórios");

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        Row header = sheet.createRow(0);
        String[] colunas = {
                "ID", "Mês/Ano", "Data Atualização", "Usuário",
                "Enc. BPC", "Enc. Aposentadoria", "Enc. Assistência Social", "Enc. Cursos Fora",
                "Enc. Cursos Dentro", "Enc. Educação Não Formal", "Enc. Educação Formal", "Enc. Documentos",
                "Enc. Trabalho", "Enc. Geração de Renda", "Enc. Saúde", "Enc. Trat. Drogas",
                "Enc. Prog. Transf. Renda", "Enc. Políticas Públicas",
                "Alimentação", "Pessoas Presenciais", "Cestas Básicas", "Kits Higiene",
                "Participantes Distância", "Participantes Presenciais",
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
        int colOffset = 4;
        int[] totais = new int[colunas.length - colOffset];

        for (Relatorio r : relatorios) {
            Row row = sheet.createRow(rowIdx++);
            int col = 0;

            row.createCell(col++).setCellValue(r.getId());
            row.createCell(col++).setCellValue(r.getMesAno());
            row.createCell(col++).setCellValue(String.valueOf(r.getDataAtualizacao()));
            row.createCell(col++).setCellValue(r.getUsuario() != null ? r.getUsuario().getNome() : "-");

            Encaminhamento e = r.getEncaminhamento();
            adicionarValor(row, totais, e != null ? e.getEncBeneficioPrestacaoContinuada() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncAposentadoria() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncAssistenciaSocial() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncCursosProfissionalizantesForaOrganizacao() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncCursosProfissionalizantesDentroOrganizacao() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncEducacaoNaoFormal() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncEducacaoFormal() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncDocumentos() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncTrabalho() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncGeracaoRenda() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncSaude() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncTratamentoDrogas() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncProgramasTransferenciaRenda() : 0, col++);
            adicionarValor(row, totais, e != null ? e.getEncPoliticasPublicas() : 0, col++);

            OutrosNumeros o = r.getOutrosNumeros();
            adicionarValor(row, totais, o != null ? o.getAlimentacao() : 0, col++);
            adicionarValor(row, totais, o != null ? o.getNumeroDePessoasPresencial() : 0, col++);
            adicionarValor(row, totais, o != null ? o.getCestasBasicasDoadas() : 0, col++);
            adicionarValor(row, totais, o != null ? o.getKitsHigieneDoados() : 0, col++);
            adicionarValor(row, totais, o != null ? o.getTotalParticipantesAtividadeDistancia() : 0, col++);
            adicionarValor(row, totais, o != null ? o.getTotalParticipantesAtividadePresencial() : 0, col++);

            AcoesRealizadas a = r.getAcoesRealizadas();
            adicionarValor(row, totais, a != null ? a.getTotalAtividadesGrupoVirtual() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalAtividadesCulturaisExternas() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalAtividadesCulturaisVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPalestrasPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPalestrasVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalVisitasFamiliaresPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalVisitasFamiliaresVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalVisitasMonitoradasPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalVisitasMonitoradasVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalCursosMinistradosPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalCursosMinistradosVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPessoasCursosCapacitacaoPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPessoasCursosCapacitacaoVirtuais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPessoasCursosProfissionalizantesPresenciais() : 0, col++);
            adicionarValor(row, totais, a != null ? a.getTotalPessoasCursosProfissionalizantesVirtuais() : 0, col++);
        }

        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(0).setCellValue("TOTAL GERAL");
        for (int i = 0; i < totais.length; i++) {
            totalRow.createCell(i + colOffset).setCellValue(totais[i]);
        }

        for (int i = 0; i < colunas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }

    private static void adicionarValor(Row row, int[] totais, int valor, int colIdx) {
        row.createCell(colIdx).setCellValue(valor);
        totais[colIdx - 4] += valor;
    }
}