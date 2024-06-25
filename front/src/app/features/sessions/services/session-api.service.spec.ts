import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all sessions', () => {
    const mockSessions: Session[] = [
      {id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]},
      {id: 2, name: 'Session 2', description: 'Description 2', date: new Date(), teacher_id: 2, users: [2, 3]}
    ]

    service.all().subscribe(sessions => {
      expect(sessions.length).toBe(2);
      expect(sessions).toEqual(mockSessions);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessions); // Simulate the response from the backend

  })

  it('should retrieve a session by id', () => {
    const mockSession: Session = {id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]}

    service.detail('1').subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('should delete a session by id', () => {
    const mockSession: Session = {id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]}

    service.delete('1').subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(mockSession);
  });

  it('should create a session', () => {
    const mockSession: Session = {id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]}

    service.create(mockSession).subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    req.flush(mockSession);
  });

  it('should update a session by id', () => {
    const mockSession: Session = {id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2]}

    service.update('1', mockSession).subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockSession);
  });

  it('should participate a user in a session', () => {
    service.participate('1', '1').subscribe(() => {});

    const req = httpTestingController.expectOne('api/session/1/participate/1');
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unparticipate a user in a session', () => {
    service.unParticipate('1', '1').subscribe(() => {});

    const req = httpTestingController.expectOne('api/session/1/participate/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
