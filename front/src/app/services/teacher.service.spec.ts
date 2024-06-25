import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';

describe('TeacherService', () => {
  let service: TeacherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    service = TestBed.inject(TeacherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all teachers', () => {
    const teachers = [
      { id: '1', name: 'John Doe' },
      { id: '2', name: 'Jane Smith' },
    ];

    service.all().subscribe((result) => {
      expect(result).toEqual(teachers);
    });
  });

  it('should retrieve teacher details by id', () => {
    const teacherId = '1';
    const teacher = { id: teacherId, name: 'John Doe' };

    service.detail(teacherId).subscribe((result) => {
      expect(result).toEqual(teacher);
    });
  });
});
