import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { By } from '@angular/platform-browser';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule],
      providers: [{ provide: SessionService, useValue: mockSessionService }]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display create and edit button if user is admin', () => {

    const createButton = fixture.debugElement.queryAll(By.css('button[routerLink="create"]'));
    expect(createButton).toBeTruthy();

    const editButton = fixture.debugElement.queryAll(By.css('button[routerLink="update"]'));
    expect(editButton).toBeTruthy();
  
  })

  it('should not display create and edit button if user is not admin', () => {

    mockSessionService.sessionInformation = {
      admin: false
    };

    fixture.detectChanges(); // apply changes of sessionInformation

    const createButton = fixture.debugElement.queryAll(By.css('button[routerLink="create"]'));
    expect(createButton.length).toBe(0);

    const editButton = fixture.debugElement.queryAll(By.css('button[routerLink="update"]'));
    expect(editButton.length).toBe(0);
  });  
});
