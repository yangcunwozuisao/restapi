package ps2.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisciplinaController {

    private List<Disciplina> disciplinas;

    public DisciplinaController() {
        this.disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina(1, "Desenvolvimento de Sistema II", "DS2", "SI", "3º Semestre"));
        disciplinas.add(new Disciplina(2, "Programação de Sistema II", "PG2", "SI", "3º Semestre"));
        disciplinas.add(new Disciplina(3, "Estrutura de Dado", "ED", "SI", "3º Semestre"));
    }

    @GetMapping("/api/disciplinas")
    Iterable<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    @GetMapping("/api/disciplinas/{id}")
    Optional<Disciplina> getDisciplina(@PathVariable long id) {
        return disciplinas.stream().filter(d -> d.getId() == id).findFirst();
    }

    @PostMapping("/api/disciplinas")
    Disciplina createDisciplina(@RequestBody Disciplina disciplina) {
        long maxId = disciplinas.stream().mapToLong(Disciplina::getId).max().orElse(0);
        disciplina.setId(maxId + 1);
        disciplinas.add(disciplina);
        return disciplina;
    }

    @PutMapping("/api/disciplinas/{id}")
    Optional<Disciplina> updateDisciplina(@RequestBody Disciplina disciplinaRequest, @PathVariable long id) {
        Optional<Disciplina> opt = getDisciplina(id);
        if (opt.isPresent()) {
            Disciplina disciplina = opt.get();
            disciplina.setNome(disciplinaRequest.getNome());
            disciplina.setSigla(disciplinaRequest.getSigla());
            disciplina.setCurso(disciplinaRequest.getCurso());
            disciplina.setSemestre(disciplinaRequest.getSemestre());
        }
        return opt;
    }

    @DeleteMapping("/api/disciplinas/{id}")
    void deleteDisciplina(@PathVariable long id) {
        disciplinas.removeIf(d -> d.getId() == id);
    }
}