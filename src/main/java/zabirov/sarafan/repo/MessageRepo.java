package zabirov.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import zabirov.sarafan.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
