# Atlas 

> "You don't have to carry the world by yourself." - Atlas

Atlas is an application which will share the burden of your workload. Offload the task of remembering tasks to Atlas.

Atlas is,
- interactive
- fast
- intuitive to use

To get the support of this mighty Titan,
1. download it from this [repo](https://github.com/wailon18/ip)
2. run the application
3. talk about your troubles (add your tasks)
4. let it assist you in carrying these burdens

Imagine having this (but without having to lift your fingers from the keyboard):
- [x] submit iP
- [ ] submit tP
- [ ] ~drop out~ change major 🫠 

Here's a sneak peek to the magic `AtlasTaskList` that happens behind the scenes:
```java
public class AtlasTaskList {
    private List<Task> taskList;

    public AtlasTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
```
