import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ListComponent } from './list.component';
import { SessionService } from 'src/app/services/session.service';
import { of } from 'rxjs';
import { expect } from '@jest/globals';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { SessionApiService } from '../../services/session-api.service';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  beforeEach(async () => {
    // Simulate a SessionService that returns two sessions
    const mockSessionApiService = {
    all: jest.fn().mockReturnValue(of([
      { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]},
      { id: 2, name: 'Session 2', description: 'Description 2', date: new Date(), teacher_id: 2, users: [1, 2, 3]}
    ]))
};

    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule, 
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        RouterTestingModule
    ],
      declarations: [ListComponent],
      providers: [
        { provide : SessionApiService, useValue: mockSessionApiService},
        { provide: SessionService, useValue: mockSessionService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should render all the sessions', () => {
    const sessionElements = fixture.nativeElement.querySelectorAll('.item');
    expect(sessionElements.length).toBe(2);
    expect(sessionElements[0].textContent).toContain('Session 1');
    expect(sessionElements[1].textContent).toContain('Session 2');
  });
});