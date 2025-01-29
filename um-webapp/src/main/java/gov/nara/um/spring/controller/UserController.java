package gov.nara.um.spring.controller;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.um.persistence.dto.TaskDTO;
import gov.nara.um.persistence.dto.TaskRewardDTO;
import gov.nara.um.persistence.dto.UserDTO;
import gov.nara.um.persistence.model.Task;
import gov.nara.um.persistence.model.TaskReward;
import gov.nara.um.persistence.model.TaskRewardPreference;
import gov.nara.um.persistence.model.User;
import gov.nara.um.service.ITaskService;
import gov.nara.um.service.IUserService;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping(value = UmMappings.USERS)
@CrossOrigin(origins = "*")
public class UserController extends AbstractLongIdController<User> implements ILongIdSortingController<User> {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITaskService taskService;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<User> findAllPaginatedAndSorted( final int page,  final int size, final String sortBy, final String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllPaginatedAndSortedDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                   @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        List<User> userList = findAllPaginatedAndSorted(page, size, sortBy, sortOrder);
        return buildDTOListFromUsers(Optional.ofNullable(userList));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<User> findAllPaginated( final int page,final int size) {
        return findPaginatedInternal(page, size);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllPaginatedDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        List<User> userList =  findAllPaginated(page, size);
        return buildDTOListFromUsers(Optional.ofNullable(userList));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/sorted
    // Unit testing  : DONE
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<User> findAllSorted(final String sortBy, final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllSortedDTO(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        List<User> userList = findAllSorted(sortBy, sortOrder);
        return buildDTOListFromUsers(Optional.ofNullable(userList));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<User> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllDTO(final HttpServletRequest request ) {
        List<User> userList = findAll(request);
        return buildDTOListFromUsers(Optional.ofNullable(userList));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public User findOne(final Long id) {return findOneInternal(id);}
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO findOneDTO(@PathVariable("id") final Long id) {
        User user = findOne(id);
        return buildDTOFromUser(user);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void create(@RequestBody final User resource) {
        createInternal(resource);
    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createDTO(@RequestBody @Valid final UserDTO resource) {
        create(buildUserFromDTO(resource));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // update - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody @Valid final UserDTO resource) {
        updateInternal(id, buildUserFromDTO(resource));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Delete - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    private User buildUserFromDTO(UserDTO dto){
        User user = new User();
        user.setName(dto.getUser_name());
        user.setUser_type(dto.getUser_type());
        user.setEmail(dto.getEmail());
        user.setTasks(buildTaskFromDTOs(dto.getTasks()));
        return  user;
    }
    private HashSet<Task> buildTaskFromDTOs(HashSet<TaskDTO> taskDTOs){
        HashSet<Task> tasks = new HashSet<>();
        if(taskDTOs != null)
            taskDTOs.forEach(taskDTO ->{
                Task bUnit = taskService.findOne(taskDTO.getId());
                tasks.add(bUnit);
            });
        return tasks;
    }
    private UserDTO buildDTOFromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUser_name(user.getName());
        userDTO.setUser_type(user.getUser_type());
        userDTO.setTasks(buildDTOsFromTasks(user.getTasks()));
        userDTO.setEmail(user.getEmail());
        return  userDTO;
    }
    private HashSet<TaskDTO> buildDTOsFromTasks(Set<Task> tasks){
        HashSet<TaskDTO> taskDTOs = new HashSet<>();
        if(tasks != null)
            tasks.forEach( task -> {
                taskDTOs.add(buildDTOFromTask(task));
        });
        return  taskDTOs;
    }
    private List<UserDTO> buildDTOListFromUsers(Optional<List<User>> userList){
        List<UserDTO> DTOList = new ArrayList<>();
        userList.ifPresent(
                users-> {
                    users.forEach(user ->{
                        DTOList.add(buildDTOFromUser(user));
                    });
        });

        return DTOList;
    }
    private TaskDTO buildDTOFromTask(Task bUnit){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(bUnit.getId());
        taskDTO.setName(bUnit.getName());
        taskDTO.setTaskDescription(bUnit.getTaskDescription());
        taskDTO.setTaskTime(bUnit.getTaskTime());
        taskDTO.setTaskRewards(buildIDsFromTaskRewardPreferences(Optional.ofNullable(bUnit.getTaskRewardPreferences())));
        return taskDTO;
    }
    private HashSet<TaskRewardDTO> buildIDsFromTaskRewardPreferences(Optional<Set<TaskRewardPreference>> taskRewardPreferences){
        HashSet<TaskRewardDTO> taskRewardDTOs = new HashSet<>();
        taskRewardPreferences.ifPresent(
                rewardPreferences->{
                    rewardPreferences.forEach(rewardPreference->{
                        TaskReward reward = rewardPreference.getTaskRewardID();
                        // build TaskReward DTO from reward
                        TaskRewardDTO dto = new TaskRewardDTO();
                        dto.setId(reward.getId());
                        dto.setName(reward.getName());
                        taskRewardDTOs.add(dto);
                    });
                }
        );
        return  taskRewardDTOs;
    }
    @Override
    protected final IUserService getService() {
        return userService;
    }

}
