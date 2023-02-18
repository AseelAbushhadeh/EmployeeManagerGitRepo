import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Employee } from './employee';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  public employees: Employee[] =[] ;
  public editEmployee!: Employee;
  public deleteEmployee!: Employee;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.getEmployees();
  }

  public getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onOpenModal(employee : Employee | null, mode: string): void {
    //to get the html <div id="main-container"
    const container = document.getElementById('main-container')

    const button = document.createElement('button');
    //by default html button <button will be of type submit
    button.type = 'button';
    //hide it in the ui
    button.style.display = 'hide';

    // the # is to indicate the att data-target is refrencing html id <div  id="updateEmployeeModal"
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    if (mode === 'edit' && employee!=null) {
      //to know which employee we're clicking on we pass the employee
      this.editEmployee = employee;
      button.setAttribute('data-target', '#updateEmployeeModal');
    }
    if (mode === 'delete' && employee != null) {
      this.deleteEmployee = employee;
      button.setAttribute('data-target', '#deleteEmployeeModal');
    }

    //add the button element inside the container
    container?.appendChild(button);
    button.click();
  }

  public onAddEmloyee(addForm: NgForm): void {
    //this is to close the add page when it appears
    document.getElementById('add-employee-form')?.click();

    //dependeing on the name att of the html elemnet
    //addForm.value will be the json representation of the for dependeing on the name {"name":".."}
    //subscripe is to be notified if the server(backend-java) returns a response, otherwise we will do something else
    this.employeeService.addEmployee(addForm.value).subscribe(
      (response: Employee) => {
        console.log(response);
        // refresh  employees list
        this.getEmployees();
        //this is to clear last data entry added to this form
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    ); 

  }

  public onUpdateEmloyee(employee?: Employee): void {
    //this is how this function is called
    // <button (click)="onUpdateEmloyee(editForm.value)"
    if (employee!=null)
    this.employeeService.updateEmployee(employee.id, employee).subscribe(
      (response: Employee) => {
        console.log(response);
        // refresh  employees list
        this.getEmployees();
      },
      (error: HttpErrorResponse) => { alert(error.message); }
    );

  }

  public onDeleteEmloyee(employeeid?: number): void {
     //this is how this function is called
    // <button (click)="onDeleteEmloyee(deleteEmployee?.id)"
    if (employeeid != null)
    this.employeeService.deleteEmployee(employeeid).subscribe(
      (response: void) => {
        console.log(response);
        this.getEmployees();
      },
      (error: HttpErrorResponse) => { alert(error.message); }
    );
  }

  public searchEmployees(key: string): void {
    console.log(key);
    const results: Employee[] = [];
    for (const employee of this.employees) {
      if (employee.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || employee.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || employee.phone.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || employee.jobTitle.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(employee);
      }
    }
    this.employees = results;
    if (results.length === 0 || !key) {
      this.getEmployees();
    }
  }

}
