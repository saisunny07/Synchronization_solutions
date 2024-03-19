This project is implemented in Java to devise software solutions for synchronization problems using semaphores for the following tasks:

	Dining Philosophers
	Post Office Simulation
	Readers-Writers problem
	Command Line

Task 1: Dining Philosophers

The Dining Philosophers problem is a classic synchronization problem often used to illustrate the challenges of concurrent programming. It involves a group of philosophers who sit at a table with a bowl 
of spaghetti in front of each of them and a fork between each pair of adjacent philosophers. The philosophers spend their time alternating between thinking and eating. However, to eat, a philosopher needs to
pick up both the fork to their left and the fork to their right. This can lead to a deadlock situation where each philosopher picks up the fork to their left, waiting for the fork to their right to become
available.


Procedure
Prompt the user for P and M, where P is the number of philosophers and M is the total number of meals to be eaten. After prompting the user for P and M, fork a single thread for each philosopher.
There will be a number of chopsticks available equal to the number of philosophers (P). Each thread will then run through the Dining Philosophers algorithm, which is shown below:

	1. Sit down at table.
	2. Pick up left chopstick.
	3. Pick up right chopstick.
	4. Begin eating.
	5. Continue eating for 3–6 cycles.
	6. Put down left chopstick.
	7. Put down right chopstick.
	8. Begin thinking.
	9. Continue thinking for 3–6 cycles.
	10. IF all meals have not been eaten, GOTO 2.
		ELSE leave the table.

As this is an extraordinarily polite group of philosophers, they hold themselves to the highest standards of etiquette. All philosophers must enter the room together and none should sit down until all are
present. Likewise, no philosopher should get up from the table to leave until all are ready to do so.

The chopsticks can be represented as an array of semaphores. The philosophers themselves are seated in a circle such that philosopher N has access to chopstick N on their left and chopstick (N+1)"\%" P on
their right. These chopsticks are the shared resource that you must control access to as part of your solution. A philosopher can only pick up a chopstick when it is available. If the chopstick is not
available, then the philosopher must be put to sleep by the semaphore.

As the philosophers progress through the algorithm, they must produce output. Specifically, they must produce output at steps 1, 2, 3, 4, 6, 7, 8, and 11 in the algorithm outlined above. All output must
specify which philosopher is producing the output, as well as any other identifiers involved such as chopstick numbers, or number of meals eaten so far.
Once the total number of meals have been eaten, no philosopher should eat any additional meals. It is acceptable for such a philosopher to proceed normally through the algorithm, with associated output, as
long as no additional meals are eaten.

The solution for this problem is implemented using semaphores for synchronization control. The only busy waiting loops used are the ones that allow a philosopher to eat and think for random amounts of time.
And as a part of the task the runtime in milliseconds for 3 different sets of parameters are recorded, each time, varying the random seed, number of philosophers, and number of meals.


Task 2: Post Office Simulation

This task is about implementating simulation of a post office. Prompt the user for N, S, and M, where N is the number of people participating in the simulation, S is the number of messages a person’s mailbox
can hold, and M is the total number of messages to be sent before the simulation ends.

Procedure
After prompting the user for N, S, and M, fork several threads equal to the number of people involved in the simulation. Each person then proceeds according to the following algorithm:

	1. Enter the post office.
	2. Read a message in that person’s mailbox.
	3. Call V() on a semaphore corresponding to that person’s mailbox.
	4. Yield.
	5. IF there are more messages to read, GOTO 2.
	   ELSE compose a message addressed to a random person other than themselves.
	6. Call P() on a semaphore corresponding to the recipient’s mailbox.
	7. Place the message in their mailbox.
	8. Leave the post office.
	9. Wait for 3–6 cycles.
	10. GOTO 1.

For this task, the mailboxes are the critical resource that must be protected via synchronization techniques. Like the chopsticks in Task 1, you must control access to the mailboxes as part of your solution.
The messages that each person sends should be randomly selected from a list.
Each person must be aware of the total number of messages sent and should not attempt to send a message if this limit has been reached. It is acceptable for such a person to proceed through the algorithm as
usual, with associated output, as long as no messages are sent.


Task 3: Readers-Writer Problem

For this task, I implemented a solution to Readers-Writers problem. Prompt the user for R, W, and N, where R is the number of readers, W is the number of writers, and N is the max number of readers that can
read the file at once. Semaphores are used to control file access when attempting to read/write the file.

Procedure
After prompting the user for R, W, and N, fork several threads equal to the number of readers and writers that access a file to read or write. There is no need to actually read or write to a file, wait for
some cycles or do some work to represent read/write latency. Readers only read the information from the file and does not change file contents. Writers can change the file contents. The basic synchronization
constraint is the any number of readers should be able to access the file at the same time, but only one writer can write to the file at a time (without any readers).

This task is designed such that N readers read, 1 writer writes, N readers read, 1 writer writes and so on. If a reader or writer is not available at a time, your solution must wait until they become available,
and the pattern must be maintained. Yield is not allowed for this task.

	
Task 4: Command Line

Designing command line argument to your the program that will select which task to run. Invalid values should result in an error message.
The list of valid arguments is as follows:

	Command Line Argument Options

	Argument	Task
	-A 1	Task 1: Dining Philosophers
	-A 2	Task 2: Post Office Simulation
	-A 3	Task 3: Readers-Writer Problem
