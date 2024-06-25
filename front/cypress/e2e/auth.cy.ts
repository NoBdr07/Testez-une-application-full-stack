describe('Testing register component', () => {
    it('Register successfull', () => {
      cy.visit('/register');
  
      cy.intercept('POST', '/api/auth/register', {
        body: {
          email: 'test@mail.com',
          firstName: 'firstName',
          lastName: 'lastName',
          password: 'password',
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
      cy.get('input[formControlName=firstName]').type('firstName');
      cy.get('input[formControlName=lastName]').type('lastName');
      cy.get('input[formControlName=password]').type(
        `${'password'}{enter}{enter}`
      );
  
      cy.url().should('include', '/login');
    });
  });
  
  describe('Testing login component', () => {
    it('Login successfull', () => {
      cy.visit('/login');
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true,
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
        `${'test!1234'}{enter}{enter}`
      );
  
      cy.url().should('include', '/sessions');
    });
  });