package gov.nara.um.persistence.model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//@Table(name = "business_unit_config_values", schema = "oif_ods")
public class TaskRewardConfig {
    @EmbeddedId
    private TaskRewardsConfigID id = new TaskRewardsConfigID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("taskID")
    @JoinColumn(name="task_id", nullable=false)
    private Task taskID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("taskRewardID")
    @JoinColumn(name="task_reward_id", nullable=false)
    private TaskReward taskRewardID;

    @Column(name = "task_reward_value")
    private String taskRewardValue;

    public TaskRewardConfig() {
    }
    public TaskRewardConfig(Task taskID, TaskReward taskConfigID) {
        this.taskID = taskID;
        this.taskRewardID = taskConfigID;
    }
}
