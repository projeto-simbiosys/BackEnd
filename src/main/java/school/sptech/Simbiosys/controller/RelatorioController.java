package school.sptech.Simbiosys.controller;

import adapter.RelatorioExcelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.dto.*;
import school.sptech.Simbiosys.exception.DadosInvalidosException;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.model.Usuario;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.repository.UsuarioRepository;
import school.sptech.Simbiosys.service.RelatorioService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "${cors.allowed.origin}")
public class RelatorioController {

    @Autowired
    private RelatorioService service;
    @Autowired
    private UsuarioRepository usuarioRepository;
    private static final String CAMINHO_PASTA = "/tmp/relatorios/";

    @PostMapping
    public ResponseEntity<RelatorioResponseDto> cadastrar (@RequestBody Relatorio relatorio, Authentication authentication) {
        try {
            UsuarioDetalhesDto usuarioDetalhes = (UsuarioDetalhesDto) authentication.getPrincipal();
            Usuario usuario = usuarioRepository.findByEmail(usuarioDetalhes.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            relatorio.setUsuario(usuario);
            Relatorio relatorioSalvo = service.cadastrar(relatorio);
            RelatorioResponseDto relatorioResponseDto = new RelatorioResponseDto(relatorioSalvo);
            relatorioResponseDto.setUsuario(UsuarioMapper.of(usuario));
            return ResponseEntity.status(201).body(relatorioResponseDto);
        } catch (DadosInvalidosException e) {
            return ResponseEntity.status(400).build();
        }catch (EntidadeJaExistente e) {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/ano/{ano}/listar")
    public ResponseEntity<List<Relatorio>> listar(@PathVariable String ano) {
        List<Relatorio> relatorios = service.buscarRelatoriosPorAno(ano);
        if (relatorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Integer id) {
        try {
            Relatorio relatorio = service.buscarPorId(id);
            return ResponseEntity.ok(relatorio);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(204).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RelatorioResponseDto> atualizar(@PathVariable Integer id, @RequestBody Relatorio relatorioInput, Authentication authentication) {
        UsuarioDetalhesDto usuarioDetalhes = (UsuarioDetalhesDto) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(usuarioDetalhes.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        relatorioInput.setUsuario(usuario);

        Relatorio atualizado = service.atualizar(id, relatorioInput);

        RelatorioResponseDto relatorioResponseDto = new RelatorioResponseDto(relatorioInput);
        relatorioResponseDto.setUsuario(UsuarioMapper.of(usuario));
        return ResponseEntity.ok(relatorioResponseDto);
    }

    @GetMapping("/mesAno/{mesAno}")
    public ResponseEntity<Relatorio> buscarPorMesAno(@PathVariable String mesAno) {
        try {
            Relatorio relatorio = service.buscarPorMesAno(mesAno);
            return ResponseEntity.ok(relatorio);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Relatorio> getRelatorioSomadoPorAno(@PathVariable String ano) {
        Relatorio relatorioSomado = service.somarRelatoriosPorAno(ano);
        return ResponseEntity.ok(relatorioSomado);
    }

    // /relatorios/periodo?de=01/2024&para=04/2024
    @GetMapping("/periodo")
    public Relatorio getRelatorioSomadoPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) {
        return service.somarRelatoriosPorPeriodo(de, para);
    }

    // ========= FLUXO 1 - EXPORTAÇÃO E RETORNO DO LINK ===========

    @GetMapping("/exportar/ano/{ano}")
    public ResponseEntity<String> exportarPorAno(@PathVariable String ano) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorAno(ano);
        String nomeArquivo = "relatorio_ano_" + ano + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    ///relatorios/exportar/mes?mesAno=01/2025
    @GetMapping("/exportar/mes")
    public ResponseEntity<String> exportarPorMes(@RequestParam String mesAno) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(mesAno, mesAno);
        String nomeArquivo = "relatorio_mes_" + mesAno.replace("/", "-") + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    @GetMapping("/exportar/periodo")
    public ResponseEntity<String> exportarPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(de, para);
        String nomeArquivo = "relatorio_periodo_" + de.replace("/", "-") + "_para_" + para.replace("/", "-") + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    // ========= FLUXO 2 - DOWNLOAD =========

    @GetMapping("/download/{nomeArquivo}")
    public ResponseEntity<InputStreamResource> downloadRelatorio(@PathVariable String nomeArquivo) throws IOException {
        File arquivo = new File(CAMINHO_PASTA + nomeArquivo);

        if (!arquivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(arquivo));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(arquivo.length())
                .body(resource);
    }
}
