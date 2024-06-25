
describe('Testing me component', () => {
    beforeEach(() => {
      cy.visit('/login');
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 2,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false,
        },
      });
  
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []
      ).as('session');
  
      cy.get('input[formControlName=email]').type('test@mail.com');
      cy.get('input[formControlName=password]').type(
        `${'test123'}{enter}{enter}`
      );
  
      cy.intercept('GET', '/api/user/2', {
        body: {
          id: 2,
          email: 'test@mail.com',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false,
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
      });
  
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []
      ).as('session');
    });
  
    it('should display  the user informations', () => {
      cy.contains('Account').click();
      cy.url().should('include', '/me');
      cy.get('p:contains("Name:")').should('contain', 'firstName LASTNAME');
      cy.get('p:contains("Email:")').should('contain', 'test@mail.com');
      cy.get('button:contains("Delete")').should('exist');
    });
  
    it('should delete the user', () => {
      cy.intercept('DELETE', '/api/user/2', {
        body: {
          id: 2,
          email: 'test@mail.com',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false,
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
      }).as('deleteUser');
  
      cy.contains('Account').click();
      cy.url().should('include', '/me');
      cy.get('button:contains("Delete")').click();
      // check if the delete message is displayed
      cy.get('.mat-simple-snack-bar-content').should('exist');
      cy.wait('@deleteUser');
      cy.url().should('include', '/');
    });
  
    it('should allow user to go back', () => {
      cy.contains('Account').click();
      cy.url().should('include', '/me');
      cy.get('button').contains('mat-icon', 'arrow_back').click();
      cy.url().should('include', '/sessions');
      cy.url().should('not.include', '/me');
    });
  });